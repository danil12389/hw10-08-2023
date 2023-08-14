package org.example.atomics;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

import static java.lang.Double.doubleToRawLongBits;
import static java.lang.Double.longBitsToDouble;

public class AtomicDouble {

    private transient volatile long value;


    private static final AtomicLongFieldUpdater<AtomicDouble> updater =
            AtomicLongFieldUpdater.newUpdater(AtomicDouble.class, "value");


    public AtomicDouble(double initialValue) {
        value = doubleToRawLongBits(initialValue);
    }

    public final double get() {
        return longBitsToDouble(value);
    }

    public final double getAndSet(double newValue) {
        long next = doubleToRawLongBits(newValue);
        return longBitsToDouble(updater.getAndSet(this, next));
    }
}
