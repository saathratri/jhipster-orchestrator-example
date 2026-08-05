package com.saathratri.developer.cassandra.store;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.type.codec.ExtraTypeCodecs;
import com.saathratri.developer.cassandra.store.config.ApplicationProperties;
import com.saathratri.developer.cassandra.store.config.CRLFLogConverter;
import jakarta.annotation.PostConstruct;
// --- SAATHRATRI CHANGE: Imports for DataStax Astra DB CqlSession bean (Cassandra prod profile) ---
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;

// --- END SAATHRATRI CHANGE ---
@SpringBootApplication()
@EnableConfigurationProperties({ ApplicationProperties.class })
public class CassandrastoreApp {

    private static final Logger LOG = LoggerFactory.getLogger(CassandrastoreApp.class);

    private final Environment env;

    // --- SAATHRATRI CHANGE: ApplicationProperties field for Cassandra Astra DB configuration ---
    private ApplicationProperties applicationProperties;

    // --- END SAATHRATRI CHANGE ---

    public CassandrastoreApp(ApplicationProperties applicationProperties, Environment env) {
        // --- SAATHRATRI CHANGE: Store ApplicationProperties for Cassandra ---
        this.applicationProperties = applicationProperties;
        // --- END SAATHRATRI CHANGE ---
        this.env = env;
    }

    /**
     * Initializes cassandrastore.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            LOG.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            LOG.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        try {
            // Workaround Hazelcast issue: https://github.com/hazelcast/hazelcast/issues/26361#issuecomment-2489778475
            Class.forName(
                "org.springframework.boot.devtools.autoconfigure.DevToolsProperties",
                false,
                SpringApplication.class.getClassLoader()
            );
            System.setProperty("spring.devtools.restart.enabled", "false");
            LOG.warn(
                "Spring Boot Developer Tools restart has been disabled using System property in order to prevent issues with Hazelcast"
            );
        } catch (Exception e) {
            // Devtools not found, ignore
        }

        var app = new SpringApplication(CassandrastoreApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store"))
            .map(key -> "https")
            .orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        var hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOG.warn("The host name could not be determined, using `localhost` as fallback");
        }
        LOG.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            """

            ----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): \t{}
            ----------------------------------------------------------""",
            applicationName,
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        LOG.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            "\n----------------------------------------------------------\n\t" +
                "Config Server: \t{}\n----------------------------------------------------------",
            configServerStatus
        );
    }

    // --- SAATHRATRI CHANGE: CqlSession bean for DataStax Astra DB (prod profile only) ---
    // Downloads secure connect bundle from AWS S3, creates CqlSession with Astra credentials
    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
    public CqlSession cqlSession() {
        LOG.info(
            "\n----------------------------------------------------------\n\t" +
                "Application Properties: \t\n----------------------------------------------------------\n" +
                "\t s3fsBucketName: {}\n" +
                "\t s3fsKeyName: {}\n" +
                "\t awsRegion: {}\n",
            this.applicationProperties.getS3fsBucketName(),
            this.applicationProperties.getS3fsKeyName(),
            this.applicationProperties.getAwsRegion()
        );

        CqlSession cqlSession = null;
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(this.applicationProperties.getAwsRegion()))
            .build();
        S3ObjectInputStream bundle = null;

        try {
            LOG.info(
                "Downloading {} from S3 bucket {}...\n",
                this.applicationProperties.getS3fsKeyName(),
                this.applicationProperties.getS3fsBucketName()
            );
            S3Object o = s3.getObject(this.applicationProperties.getS3fsBucketName(), this.applicationProperties.getS3fsKeyName());
            bundle = o.getObjectContent();

            cqlSession = CqlSession.builder()
                .withCloudSecureConnectBundle(bundle)
                .addTypeCodecs(ExtraTypeCodecs.TIMESTAMP_MILLIS_UTC)
                // Pass client ID and client secret from the ASTRA environment variables.
                .withKeyspace(applicationProperties.getKeyspace())
                .withAuthCredentials(applicationProperties.getClientId(), applicationProperties.getClientSecret())
                .withConfigLoader(
                    DriverConfigLoader.programmaticBuilder()
                        // Resolves the timeout query 'SELECT * FROM system_schema.tables' timed out after PT2S
                        .withDuration(DefaultDriverOption.METADATA_SCHEMA_REQUEST_TIMEOUT, Duration.ofMillis(60000))
                        .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofMillis(60000))
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofMillis(15000))
                        .build()
                )
                .build();
        } catch (AmazonServiceException e) {
            LOG.error("Could not instantiate CQL Session.");
            LOG.error(e.getMessage(), e);
        } finally {
            if (bundle != null) {
                LOG.info("About to drain S3 Object Input Stream...");
                IOUtils.drainInputStream(bundle);
                LOG.info("Drained S3 Object Input Stream.");
                try {
                    LOG.info("About to close S3 Object Input Stream...");
                    bundle.close();
                    LOG.info("Closed S3 Object Input Stream.");
                } catch (IOException e) {
                    LOG.info(e.getMessage(), e);
                }
            }
        }

        return cqlSession;
    }
    // --- END SAATHRATRI CHANGE ---
}
