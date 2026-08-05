package com.saathratri.developer.psql.store.domain;

import java.util.UUID;

public class PsqlReportTestSamples {

    public static PsqlReport getPsqlReportSample1() {
        return new PsqlReport()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .fileName("fileName1")
            .fileExtension("fileExtension1");
    }

    public static PsqlReport getPsqlReportSample2() {
        return new PsqlReport()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .fileName("fileName2")
            .fileExtension("fileExtension2");
    }

    public static PsqlReport getPsqlReportRandomSampleGenerator() {
        return new PsqlReport().id(UUID.randomUUID()).fileName(UUID.randomUUID().toString()).fileExtension(UUID.randomUUID().toString());
    }
}
