package com.saathratri.developer.cassandra.blog;

import com.saathratri.developer.cassandra.blog.config.AsyncSyncConfiguration;
import com.saathratri.developer.cassandra.blog.config.EmbeddedCassandra;
import com.saathratri.developer.cassandra.blog.config.JacksonConfiguration;
import com.saathratri.developer.cassandra.blog.config.TestSecurityConfiguration;
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
    classes = { CassandrablogApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class }
)
@EmbeddedCassandra
public @interface IntegrationTest {}
