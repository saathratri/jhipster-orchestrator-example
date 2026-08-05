package com.saathratri.developer.psql.blog.domain;

import java.util.UUID;

public class PsqlTajUserTestSamples {

    public static PsqlTajUser getPsqlTajUserSample1() {
        return new PsqlTajUser().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).login("login1");
    }

    public static PsqlTajUser getPsqlTajUserSample2() {
        return new PsqlTajUser().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).login("login2");
    }

    public static PsqlTajUser getPsqlTajUserRandomSampleGenerator() {
        return new PsqlTajUser().id(UUID.randomUUID()).login(UUID.randomUUID().toString());
    }
}
