package com.saathratri.developer.cassandra.store;

import com.saathratri.developer.cassandra.store.config.AsyncSyncConfiguration;
import com.saathratri.developer.cassandra.store.config.EmbeddedCassandra;
import com.saathratri.developer.cassandra.store.config.JacksonConfiguration;
import com.saathratri.developer.cassandra.store.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    classes = { CassandrastoreApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedCassandra
public @interface IntegrationTest {}
