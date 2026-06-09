package com.saathratri.developer.cassandra.blog.config;

// --- SAATHRATRI CHANGE: Logger import for Cassandra Astra DB property logging ---
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

// --- END SAATHRATRI CHANGE ---
/**
 * Properties specific to Cassandrablog.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    // jhipster-needle-application-properties-property

    // jhipster-needle-application-properties-property-getter

    // jhipster-needle-application-properties-property-class
    // --- SAATHRATRI CHANGE: AWS S3 and DataStax Astra DB properties for Cassandra microservices ---

    private static final Logger logger = LoggerFactory.getLogger(ApplicationProperties.class);

    private String s3fsBucketName;
    private String s3fsKeyName;
    private String awsRegion;

    private String clientId;
    private String clientSecret;
    private String keyspace;

    public String getS3fsBucketName() {
        return this.s3fsBucketName;
    }

    public void setS3fsBucketName(String s3fsBucketName) {
        this.s3fsBucketName = s3fsBucketName;
    }

    public String getS3fsKeyName() {
        return this.s3fsKeyName;
    }

    public void setS3fsKeyName(String s3fsKeyName) {
        this.s3fsKeyName = s3fsKeyName;
    }

    public String getAwsRegion() {
        return this.awsRegion;
    }

    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(String keyspace) {
        this.keyspace = keyspace;
    }

    public void printProperties() {
        logger.info("\nEnvironment Variables Passed Via source ±/.datastax.env:");
        logger.info("ASTRA_DB_CLIENT_ID = {}", clientId);
        logger.info("ASTRA_DB_CLIENT_SECRET = {}\n\n", clientSecret);
        logger.info("ASTRA_DB_REGION = {}", awsRegion);
        logger.info("ASTRA_DB_KEYSPACE = {}", keyspace);
    }
    // --- END SAATHRATRI CHANGE ---
}
