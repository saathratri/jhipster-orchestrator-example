package com.saathratri.developer.psql.blog.service.embedding;

import com.saathratri.developer.psql.blog.domain.PsqlTag;
import com.saathratri.developer.psql.blog.repository.PsqlTagRepository;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for migrating existing entities to populate their vector embeddings.
 * This is a one-time migration service that can be triggered via REST endpoint or CLI.
 */
@Service
@Transactional
public class EmbeddingMigrationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmbeddingMigrationService.class);

    private static final int BATCH_SIZE = 100;
    private static final long RATE_LIMIT_DELAY_MS = 100; // Delay between API calls to respect rate limits

    private final EmbeddingService embeddingService;
    private final PsqlTagRepository psqlTagRepository;

    public EmbeddingMigrationService(EmbeddingService embeddingService, PsqlTagRepository psqlTagRepository) {
        this.embeddingService = embeddingService;
        this.psqlTagRepository = psqlTagRepository;
    }

    /**
     * Migrate all entities with missing embeddings.
     * This method processes all entity types sequentially.
     *
     * @return summary of migration results
     */
    public String migrateAllEmbeddings() {
        if (!embeddingService.isAvailable()) {
            return "Embedding service is not available. Configure OpenAI API key first.";
        }

        StringBuilder summary = new StringBuilder();
        summary.append("=== Embedding Migration Summary ===\n");

        summary.append(migratePsqlTagEmbeddings()).append("\n");

        summary.append("=== Migration Complete ===");
        LOG.info(summary.toString());
        return summary.toString();
    }

    /**
     * Migrate all entities asynchronously.
     * Useful for large datasets to avoid blocking.
     */
    @Async
    public void migrateAllEmbeddingsAsync() {
        LOG.info("Starting async embedding migration...");
        String result = migrateAllEmbeddings();
        LOG.info("Async embedding migration completed: {}", result);
    }

    /**
     * Migrate embeddings for PsqlTag entities.
     *
     * @return migration result summary
     */
    public String migratePsqlTagEmbeddings() {
        LOG.info("Starting PsqlTag embedding migration...");
        AtomicInteger processed = new AtomicInteger(0);
        AtomicInteger updated = new AtomicInteger(0);
        AtomicInteger errors = new AtomicInteger(0);

        List<PsqlTag> entities = psqlTagRepository.findAll();
        LOG.info(": Found {} total records", entities.size());

        for (PsqlTag entity : entities) {
            try {
                boolean wasUpdated = false;

                // Generate embedding for name -> nameEmbedding
                if (entity.getNameEmbedding() == null && entity.getName() != null) {
                    // Use vector string format for pgvector compatibility: "[0.1, 0.2, ...]"
                    float[] embedding = embeddingService.generateEmbedding(entity.getName());
                    if (embedding != null) {
                        entity.setNameEmbedding(embedding);
                        wasUpdated = true;
                    }
                    rateLimitDelay();
                }

                // Generate embedding for description -> descriptionEmbedding
                if (entity.getDescriptionEmbedding() == null && entity.getDescription() != null) {
                    // Use vector string format for pgvector compatibility: "[0.1, 0.2, ...]"
                    float[] embedding = embeddingService.generateEmbedding(entity.getDescription());
                    if (embedding != null) {
                        entity.setDescriptionEmbedding(embedding);
                        wasUpdated = true;
                    }
                    rateLimitDelay();
                }

                if (wasUpdated) {
                    psqlTagRepository.save(entity);
                    updated.incrementAndGet();
                }
                processed.incrementAndGet();

                if (processed.get() % BATCH_SIZE == 0) {
                    LOG.info("PsqlTag: Processed {} entities, updated {}", processed.get(), updated.get());
                }
            } catch (Exception e) {
                errors.incrementAndGet();
                LOG.error("Error processing PsqlTag id={}: {}", entity.getId(), e.getMessage());
            }
        }

        String result = "PsqlTag: processed=%d, updated=%d, errors=%d".formatted(processed.get(), updated.get(), errors.get());
        LOG.info(result);
        return result;
    }

    private void rateLimitDelay() {
        try {
            Thread.sleep(RATE_LIMIT_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
