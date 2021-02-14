package com.mutithread.atomic;

import sun.misc.Unsafe;

/**
 * 自定义一个AtomicInteger
 */
public class AtoimcData {

    private volatile int value;
    static final Unsafe UNSAFE;
    static final long DATA_OFFSET;

    static {
        UNSAFE = UnsafeAccessor.getInstance();
        try {
            DATA_OFFSET = UNSAFE.objectFieldOffset(Unsafe.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException();
        }
    }

    public AtoimcData(int value) {
        this.value = value;
    }

    public void decrement(int amount) {
        int oldValue;
        while (true) {
            oldValue = value;
            if (UNSAFE.compareAndSwapInt(this, DATA_OFFSET, oldValue, oldValue - amount)) {
                return;
            }
        }
    }

    public int getValue() {
        return value;
    }
}
