package com.saathratri.developer.cassandra.blog.domain;

import java.util.UUID;

public class CassSaathratriEntityTestSamples {

    public static CassSaathratriEntity getCassSaathratriEntitySample1() {
        return new CassSaathratriEntity()
            .entityId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .createdId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static CassSaathratriEntity getCassSaathratriEntitySample2() {
        return new CassSaathratriEntity()
            .entityId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .entityName("entityName2")
            .entityDescription("entityDescription2")
            .createdId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static CassSaathratriEntity getCassSaathratriEntityRandomSampleGenerator() {
        return new CassSaathratriEntity()
            .entityId(UUID.randomUUID())
            .entityName(UUID.randomUUID().toString())
            .entityDescription(UUID.randomUUID().toString())
            .createdId(UUID.randomUUID())
            .createdTimeId(UUID.randomUUID());
    }
}
