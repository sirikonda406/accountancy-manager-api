package com.iriscorporate.pulse.workflow.common.testFixtures;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.range;

public final class Generators {

    private static final Random random = new Random();
    private static final int DEFAULT_SIZE_BOUND = 4;

    private Generators() {
        throw new UnsupportedOperationException();
    }

    public static <T> List<T> listOf(Supplier<T> supplier) {
        return listOf(random.nextInt(DEFAULT_SIZE_BOUND), supplier);
    }

    public static <T> List<T> listOf(int size, Supplier<T> supplier) {
        return streamOf(size, supplier).collect(toList());
    }

    public static <T> Set<T> setOf(Supplier<T> supplier) {
        return setOf(random.nextInt(DEFAULT_SIZE_BOUND), supplier);
    }

    public static <T> Set<T> setOf(int size, Supplier<T> supplier) {
        return streamOf(size, supplier).collect(toSet());
    }

    public static <T> Stream<T> streamOf(int size, Supplier<T> supplier) {
        return range(0, size).mapToObj(i -> supplier.get());
    }

    @SafeVarargs // the vararg is not modified nor returned
    public static <T> T anyOf(T... options) {
        return options[random.nextInt(options.length)];
    }

}
