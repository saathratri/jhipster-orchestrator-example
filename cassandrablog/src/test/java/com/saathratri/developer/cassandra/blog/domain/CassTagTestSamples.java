package com.saathratri.developer.cassandra.blog.domain;

import java.util.UUID;

public class CassTagTestSamples {

    public static CassTag getCassTagSample1() {
        return new CassTag().id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).name("name1").description("description1");
    }

    public static CassTag getCassTagSample2() {
        return new CassTag().id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).name("name2").description("description2");
    }

    public static CassTag getCassTagRandomSampleGenerator() {
        return new CassTag().id(UUID.randomUUID()).name(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
