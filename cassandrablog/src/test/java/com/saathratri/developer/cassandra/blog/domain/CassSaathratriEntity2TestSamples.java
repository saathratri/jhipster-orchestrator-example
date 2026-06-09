package com.saathratri.developer.cassandra.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CassSaathratriEntity2TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CassSaathratriEntity2 getCassSaathratriEntity2Sample1() {
        return new CassSaathratriEntity2()
            .compositeId(
                new CassSaathratriEntity2Id()
                    .entityTypeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .yearOfDateAdded(1L)
                    .arrivalDate(1L)
                    .blogId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L);
    }

    public static CassSaathratriEntity2 getCassSaathratriEntity2Sample2() {
        return new CassSaathratriEntity2()
            .compositeId(
                new CassSaathratriEntity2Id()
                    .entityTypeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .yearOfDateAdded(2L)
                    .arrivalDate(2L)
                    .blogId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L);
    }

    public static CassSaathratriEntity2 getCassSaathratriEntity2RandomSampleGenerator() {
        return new CassSaathratriEntity2()
            .compositeId(
                new CassSaathratriEntity2Id()
                    .entityTypeId(UUID.randomUUID())
                    .yearOfDateAdded(longCount.incrementAndGet())
                    .arrivalDate(longCount.incrementAndGet())
                    .blogId(UUID.randomUUID())
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L);
    }
}
