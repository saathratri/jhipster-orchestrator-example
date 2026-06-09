package com.saathratri.developer.cassandra.blog.domain;

import java.util.UUID;

public class CassTajUserTestSamples {

    public static CassTajUser getCassTajUserSample1() {
        return new CassTajUser().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).login("login1");
    }

    public static CassTajUser getCassTajUserSample2() {
        return new CassTajUser().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).login("login2");
    }

    public static CassTajUser getCassTajUserRandomSampleGenerator() {
        return new CassTajUser().id(UUID.randomUUID()).login(UUID.randomUUID().toString());
    }
}
