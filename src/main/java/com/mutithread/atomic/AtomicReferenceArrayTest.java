package com.mutithread.atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicReferenceArrayTest {

    private static <T> void demo(Supplier<T> arrSupplier,
                                 Function<T, Integer> lenFun,
                                 BiConsumer<T, Integer> putConsumer,
                                 Consumer<T> printConsumer) {
        List<Thread> ts = new ArrayList<>();
        T array = arrSupplier.get();
        int length = lenFun.apply(array);
        for (int i = 0; i < length; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    putConsumer.accept(array, j % length);
                }
            }));
        }

        ts.forEach(Thread::start);
        ts.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        printConsumer.accept(array);
    }


    public static void main(String[] args) {
        AtomicReferenceArrayTest.demo(
                () -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println(Arrays.toString(array))
        );

        AtomicReferenceArrayTest.demo(
                () -> new AtomicIntegerArray(10),
                array -> array.length(),
                (array, index) -> array.getAndIncrement(index),
                array -> System.out.println(array)
        );
    }
}
