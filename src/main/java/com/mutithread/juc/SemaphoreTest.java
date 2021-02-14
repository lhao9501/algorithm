package com.mutithread.juc;

import java.util.concurrent.Semaphore;

/**
 *  Semaphore 用来限制能同时访问共享资源的线程上限
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("running....");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("end....");
                } finally {
                    semaphore.release();
                }

            }).start();
        }
    }
}
