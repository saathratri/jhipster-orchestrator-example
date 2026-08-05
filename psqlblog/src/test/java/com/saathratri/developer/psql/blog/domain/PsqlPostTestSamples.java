package com.saathratri.developer.psql.blog.domain;

import java.util.UUID;

public class PsqlPostTestSamples {

    public static PsqlPost getPsqlPostSample1() {
        return new PsqlPost().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).title("title1");
    }

    public static PsqlPost getPsqlPostSample2() {
        return new PsqlPost().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).title("title2");
    }

    public static PsqlPost getPsqlPostRandomSampleGenerator() {
        return new PsqlPost().id(UUID.randomUUID()).title(UUID.randomUUID().toString());
    }
}
