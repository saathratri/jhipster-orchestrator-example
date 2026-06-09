package com.saathratri.developer.psql.blog;

import com.saathratri.developer.psql.blog.config.AsyncSyncConfiguration;
import com.saathratri.developer.psql.blog.config.DatabaseTestcontainer;
import com.saathratri.developer.psql.blog.config.JacksonConfiguration;
import com.saathratri.developer.psql.blog.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = {
        PsqlblogApp.class,
        JacksonConfiguration.class,
        AsyncSyncConfiguration.class,
        TestSecurityConfiguration.class,
        com.saathratri.developer.psql.blog.config.JacksonHibernateConfiguration.class,
    }
)
@ImportTestcontainers(DatabaseTestcontainer.class)
public @interface IntegrationTest {}
