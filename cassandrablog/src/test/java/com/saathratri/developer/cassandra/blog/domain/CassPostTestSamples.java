package com.saathratri.developer.cassandra.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CassPostTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CassPost getCassPostSample1() {
        return new CassPost()
            .compositeId(new CassPostId().createdDate(1L).addedDateTime(1L).postId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")))
            .title("title1")
            .content("content1")
            .publishedDateTime(1L)
            .sentDate(1L);
    }

    public static CassPost getCassPostSample2() {
        return new CassPost()
            .compositeId(new CassPostId().createdDate(2L).addedDateTime(2L).postId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")))
            .title("title1")
            .content("content1")
            .publishedDateTime(1L)
            .sentDate(1L);
    }

    public static CassPost getCassPostRandomSampleGenerator() {
        return new CassPost()
            .compositeId(
                new CassPostId()
                    .createdDate(longCount.incrementAndGet())
                    .addedDateTime(longCount.incrementAndGet())
                    .postId(UUID.randomUUID())
            )
            .title("title1")
            .content("content1")
            .publishedDateTime(1L)
            .sentDate(1L);
    }
}
