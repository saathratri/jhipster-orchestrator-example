package com.saathratri.developer.psql.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// --- SAATHRATRI CHANGE: Logger import for Cassandra Astra DB property logging ---
// --- END SAATHRATRI CHANGE ---
/**
 * Properties specific to Psqlblog.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    // --- SAATHRATRI CHANGE: Pre-seed Liquibase property/getter/inner-class that upstream
    // jhipster:spring-boot:liquibase#customizeApplicationProperties would otherwise try to
    // inject via needle markers in POST_WRITING. When this blueprint writes the file during
    // workspaces regen the needles are occasionally not visible to the customizer's editFile
    // pass and it throws "Missing required jhipster-needle application-properties-property".
    // Seeding here makes the upstream customizer's content check (checkContentIn) match and
    // returns early, so the file is correct regardless of ordering. ---

    private final Liquibase liquibase = new Liquibase();

    // jhipster-needle-application-properties-property

    public Liquibase getLiquibase() {
        return liquibase;
    }

    // jhipster-needle-application-properties-property-getter

    public static class Liquibase {

        private Boolean asyncStart = true;

        public Boolean getAsyncStart() {
            return asyncStart;
        }

        public void setAsyncStart(Boolean asyncStart) {
            this.asyncStart = asyncStart;
        }
    }
    // --- END SAATHRATRI CHANGE ---

    // jhipster-needle-application-properties-property-class
    // --- SAATHRATRI CHANGE: AWS S3 and DataStax Astra DB properties for Cassandra microservices ---
    // --- END SAATHRATRI CHANGE ---
}
