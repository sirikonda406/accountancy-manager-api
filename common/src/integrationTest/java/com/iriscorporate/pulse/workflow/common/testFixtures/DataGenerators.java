package com.iriscorporate.pulse.workflow.common.testFixtures;

import com.github.javafaker.Faker;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.String.format;

public final class DataGenerators {

    private static final AtomicInteger counter = new AtomicInteger(0);

    private static final Faker FAKER = new Faker();

    private DataGenerators() {
        // non instantiatable
    }

    public static Faker faker() {
        return FAKER;
    }

    public static String unique(String value) {
        return format("%s %d", value, counter.incrementAndGet());
    }

}
