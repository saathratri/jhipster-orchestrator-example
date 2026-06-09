package com.saathratri.developer.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// --- SAATHRATRI CHANGE: Logger import for Cassandra Astra DB property logging ---
// --- END SAATHRATRI CHANGE ---
/**
 * Properties specific to Orchestratorgateway.
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
    // --- END SAATHRATRI CHANGE ---
}
