package com.saathratri.developer.cassandra.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CassLandingPageByOrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CassLandingPageByOrganization getCassLandingPageByOrganizationSample1() {
        return new CassLandingPageByOrganization()
            .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .detailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("detailsText1", "detailsText1");
                    }
                }
            )
            .detailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("detailsBigInt1", 1L);
                    }
                }
            );
    }

    public static CassLandingPageByOrganization getCassLandingPageByOrganizationSample2() {
        return new CassLandingPageByOrganization()
            .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .detailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("detailsText2", "detailsText2");
                    }
                }
            )
            .detailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("detailsBigInt2", 2L);
                    }
                }
            );
    }

    public static CassLandingPageByOrganization getCassLandingPageByOrganizationRandomSampleGenerator() {
        return new CassLandingPageByOrganization()
            .organizationId(UUID.randomUUID())
            .detailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("detailsText1", "detailsText1");
                    }
                }
            )
            .detailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("detailsBigInt1", 1L);
                    }
                }
            );
    }
}
