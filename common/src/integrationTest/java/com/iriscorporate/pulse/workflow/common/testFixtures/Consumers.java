package com.iriscorporate.pulse.workflow.common.testFixtures;

import java.util.Iterator;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Consumers {

    private static final Random random = ThreadLocalRandom.current();

    private Consumers() {
        throw new UnsupportedOperationException();
    }

    public static <T> Consumer<T> occasionally(Consumer<T> consumer) {
        return oneQuarterOfTheTime(consumer).orElse(doNothing());
    }

    public static <T> Consumer<T> maybe(Consumer<T> consumer) {
        return halfTheTime(consumer).orElse(doNothing());
    }

    public static <T> Consumer<T> often(Consumer<T> consumer) {
        return threeQuartersOfTheTime(consumer).orElse(doNothing());
    }

    public static <T> Consumer<T> doNothing() {
        //@formatter:off
		return t -> {};
		//@formatter:on
    }

    private static <T> Optional<T> oneQuarterOfTheTime(T value) {
        return percentageOfTheTime(25, value);
    }

    private static <T> Optional<T> halfTheTime(T value) {
        return percentageOfTheTime(50, value);
    }

    private static <T> Optional<T> threeQuartersOfTheTime(T value) {
        return percentageOfTheTime(75, value);
    }

    private static <T> Optional<T> percentageOfTheTime(int percentage, T value) {
        return random.nextInt(100) < percentage ? Optional.of(value) : Optional.empty();
    }

    public static <T> Consumer<T> failWithCause(Supplier<? extends Exception> cause) {
        return t -> {
            throw new TestingException(cause.get());
        };
    }

    /**
     * Yields a consumer that will delegate each input in sequence to the given <code>consumers</code>.
     */
    public static <T> Consumer<T> sequentially(Iterator<Consumer<T>> consumers) {
        return t -> {
            if (consumers.hasNext()) {
                consumers.next().accept(t);
            }
        };
    }

    public static <T> Consumer<T> sequentially(Iterable<Consumer<T>> consumers) {
        return sequentially(consumers.iterator());
    }

}
