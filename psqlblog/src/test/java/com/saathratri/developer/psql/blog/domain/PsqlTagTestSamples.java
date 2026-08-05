package com.saathratri.developer.psql.blog.domain;

import java.util.UUID;

public class PsqlTagTestSamples {

    public static PsqlTag getPsqlTagSample1() {
        return new PsqlTag().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").description("description1");
    }

    public static PsqlTag getPsqlTagSample2() {
        return new PsqlTag().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").description("description2");
    }

    public static PsqlTag getPsqlTagRandomSampleGenerator() {
        return new PsqlTag().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
