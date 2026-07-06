package com.saathratri.developer.cassandra.blog.domain;

import java.util.UUID;

public class CassSetEntityByOrganizationTestSamples {

    public static CassSetEntityByOrganization getCassSetEntityByOrganizationSample1() {
        return new CassSetEntityByOrganization().organizationId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa")).tags(
            new java.util.TreeSet<String>() {
                {
                    add("tags1");
                }
            }
        );
    }

    public static CassSetEntityByOrganization getCassSetEntityByOrganizationSample2() {
        return new CassSetEntityByOrganization().organizationId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367")).tags(
            new java.util.TreeSet<String>() {
                {
                    add("tags2");
                }
            }
        );
    }

    public static CassSetEntityByOrganization getCassSetEntityByOrganizationRandomSampleGenerator() {
        return new CassSetEntityByOrganization().organizationId(UUID.randomUUID()).tags(
            new java.util.TreeSet<String>() {
                {
                    add("tags1");
                }
            }
        );
    }
}
