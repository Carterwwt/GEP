package com.carterwang.Repo;

import com.carterwang.Data.SampleData;

public class SampleDataRepo {
    private static SampleData sampleData;

    public static SampleData getSampleData() {
        return sampleData;
    }

    public static void setSampleData(SampleData sampleData) {
        SampleDataRepo.sampleData = sampleData;
    }
}
