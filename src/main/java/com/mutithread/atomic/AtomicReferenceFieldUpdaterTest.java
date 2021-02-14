package com.mutithread.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AtomicReferenceFieldUpdaterTest {
    private volatile int field;

    public static void main(String[] args) {
        /*
        // 测试 字段修改
        AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(AtomicReferenceFieldUpdaterTest.class, "field");
        AtomicReferenceFieldUpdaterTest test = new AtomicReferenceFieldUpdaterTest();

        if (updater.compareAndSet(test, 0, 10)) {
            System.out.println(test.field);
        }

        if (updater.compareAndSet(test, 10, 20)) {
            System.out.println(test.field);
        }*/

        for (int i = 0; i < 5; i++) {
            demo(() -> new AtomicLong(), adder -> adder.getAndIncrement());
        }
        System.out.println("---------------------");
        for (int i = 0; i < 5; i++) {
            demo(() -> new LongAdder(), adder -> adder.increment());
        }

    }

    public static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        long start = System.nanoTime();

        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 50000; j++) {
                    action.accept(adder);
                }
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(adder + " cost: " + (end - start)/1000_000);
    }
}
