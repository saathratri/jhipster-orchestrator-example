package com.saathratri.developer.cassandra.blog.domain;

import java.util.UUID;

public class CassSaathratriEntity4TestSamples {

    public static CassSaathratriEntity4 getCassSaathratriEntity4Sample1() {
        return new CassSaathratriEntity4()
            .compositeId(
                new CassSaathratriEntity4Id()
                    .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .attributeKey("attributeKey1")
            )
            .attributeValue("attributeValue1");
    }

    public static CassSaathratriEntity4 getCassSaathratriEntity4Sample2() {
        return new CassSaathratriEntity4()
            .compositeId(
                new CassSaathratriEntity4Id()
                    .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .attributeKey("attributeKey2")
            )
            .attributeValue("attributeValue1");
    }

    public static CassSaathratriEntity4 getCassSaathratriEntity4RandomSampleGenerator() {
        return new CassSaathratriEntity4()
            .compositeId(new CassSaathratriEntity4Id().organizationId(UUID.randomUUID()).attributeKey(UUID.randomUUID().toString()))
            .attributeValue("attributeValue1");
    }
}
