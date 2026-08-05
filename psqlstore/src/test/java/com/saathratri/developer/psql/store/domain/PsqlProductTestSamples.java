package com.saathratri.developer.psql.store.domain;

import java.util.UUID;

public class PsqlProductTestSamples {

    public static PsqlProduct getPsqlProductSample1() {
        return new PsqlProduct().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).title("title1");
    }

    public static PsqlProduct getPsqlProductSample2() {
        return new PsqlProduct().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).title("title2");
    }

    public static PsqlProduct getPsqlProductRandomSampleGenerator() {
        return new PsqlProduct().id(UUID.randomUUID()).title(UUID.randomUUID().toString());
    }
}
