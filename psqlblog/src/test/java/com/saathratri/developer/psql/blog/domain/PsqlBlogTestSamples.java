package com.saathratri.developer.psql.blog.domain;

import java.util.UUID;

public class PsqlBlogTestSamples {

    public static PsqlBlog getPsqlBlogSample1() {
        return new PsqlBlog().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").handle("handle1");
    }

    public static PsqlBlog getPsqlBlogSample2() {
        return new PsqlBlog().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").handle("handle2");
    }

    public static PsqlBlog getPsqlBlogRandomSampleGenerator() {
        return new PsqlBlog().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).handle(UUID.randomUUID().toString());
    }
}
