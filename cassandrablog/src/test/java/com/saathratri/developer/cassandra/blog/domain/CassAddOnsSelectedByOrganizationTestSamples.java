package com.saathratri.developer.cassandra.blog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CassAddOnsSelectedByOrganizationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CassAddOnsSelectedByOrganization getCassAddOnsSelectedByOrganizationSample1() {
        return new CassAddOnsSelectedByOrganization()
            .compositeId(
                new CassAddOnsSelectedByOrganizationId()
                    .organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
                    .arrivalDate(1L)
                    .accountNumber("accountNumber1")
                    .createdTimeId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }

    public static CassAddOnsSelectedByOrganization getCassAddOnsSelectedByOrganizationSample2() {
        return new CassAddOnsSelectedByOrganization()
            .compositeId(
                new CassAddOnsSelectedByOrganizationId()
                    .organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
                    .arrivalDate(2L)
                    .accountNumber("accountNumber2")
                    .createdTimeId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }

    public static CassAddOnsSelectedByOrganization getCassAddOnsSelectedByOrganizationRandomSampleGenerator() {
        return new CassAddOnsSelectedByOrganization()
            .compositeId(
                new CassAddOnsSelectedByOrganizationId()
                    .organizationId(UUID.randomUUID())
                    .arrivalDate(longCount.incrementAndGet())
                    .accountNumber(UUID.randomUUID().toString())
                    .createdTimeId(UUID.randomUUID())
            )
            .departureDate(1L)
            .customerId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .customerFirstName("customerFirstName1")
            .customerLastName("customerLastName1")
            .customerUpdatedEmail("customerUpdatedEmail1")
            .customerUpdatedPhoneNumber("customerUpdatedPhoneNumber1")
            .customerEstimatedArrivalTime("customerEstimatedArrivalTime1")
            .tinyUrlShortCode("tinyUrlShortCode1")
            .addOnDetailsText(
                new java.util.HashMap<String, String>() {
                    {
                        put("addOnDetailsText1", "addOnDetailsText1");
                    }
                }
            )
            .addOnDetailsBigInt(
                new java.util.HashMap<String, Long>() {
                    {
                        put("addOnDetailsBigInt1", 1L);
                    }
                }
            );
    }
}
