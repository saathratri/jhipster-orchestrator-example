package com.saathratri.developer.cassandra.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CassSaathratriEntity3TestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CassSaathratriEntity3 getCassSaathratriEntity3Sample1() {
        return new CassSaathratriEntity3()
            .compositeId(
                new CassSaathratriEntity3Id()
                    .entityType("entityType1")
                    .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }

    public static CassSaathratriEntity3 getCassSaathratriEntity3Sample2() {
        return new CassSaathratriEntity3()
            .compositeId(
                new CassSaathratriEntity3Id()
                    .entityType("entityType2")
                    .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }

    public static CassSaathratriEntity3 getCassSaathratriEntity3RandomSampleGenerator() {
        return new CassSaathratriEntity3()
            .compositeId(new CassSaathratriEntity3Id().entityType(UUID.randomUUID().toString()).createdTimeId(UUID.randomUUID()))
            .entityName("entityName1")
            .entityDescription("entityDescription1")
            .departureDate(1L)
            .tags(
                new java.util.TreeSet<String>() {
                    {
                        add("tags1");
                    }
                }
            );
    }
}
