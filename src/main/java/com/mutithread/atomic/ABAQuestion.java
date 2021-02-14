package com.mutithread.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j
public class ABAQuestion {
    // static AtomicReference<String> reference = new AtomicReference<>("A");

    // 断开闭环  一个方向增长
    // static AtomicStampedReference<String> reference2 = new AtomicStampedReference<>("A", 0);

    static AtomicMarkableReference<String> reference3 = new AtomicMarkableReference<>("A", false);

    public static void main(String[] args) {
        log.info("main start...");

        other();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*String prev = reference2.getReference();  // reference.get();
        int stamp = reference2.getStamp();*/

        /*if (reference.compareAndSet(prev, "B")) {
            System.out.println("main: A -> B");
        }
        if (reference2.compareAndSet(prev, "B", stamp, 3)) {
            System.out.println("main: A -> B");
        }*/
        String prev = reference3.getReference();
        if (reference3.compareAndSet(prev, "B", false, true)) {
            log.info("main: A -> B");
        }
    }

    /**
     *
     */
    public static void other() {
        new Thread(() -> {
            /*String prev = reference2.getReference(); // reference.get();
            int stamp = reference2.getStamp();*/
            /*if (reference.compareAndSet(prev, "B")) {
                System.out.println("t1: A -> B");
            }*/

            /*if (reference2.compareAndSet(prev, "B", stamp, 1)) {
                System.out.println("t1: A -> B");
            }*/
            String prev = reference3.getReference();
            if (reference3.compareAndSet(prev, "B", false, true)) {
                log.info("t1: A -> B");
            }
        }, "t1").start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            /*String prev = reference.get();
            if (reference.compareAndSet(prev, "A")) {
                System.out.println("t2: B -> A");
            }*/
            /*String prev = reference2.getReference();
            int stamp = reference2.getStamp();
            if (reference2.compareAndSet(prev, "A", stamp, 2)) {
                System.out.println("t2: B -> A");
            }*/
            String prev = reference3.getReference();
            if (reference3.compareAndSet(prev, "A", true, false)) {
                log.info("t2: B -> A");
            }
        }, "t2").start();
    }
}
