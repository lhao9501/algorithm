package com.mutithread.juc;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        mutipleThreadWait();

        /*new Thread(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        }).start();
        new Thread(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        }).start();
        new Thread(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        }).start();

        System.out.println("main begin...");
        try {
            latch.await();  // count != 0 被阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end... count = " + latch.getCount());*/

        /*// 配合 线程池 使用
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.submit(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        });
        pool.submit(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        });
        pool.submit(() -> {
            System.out.println("begin...");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
            System.out.println("end... count = " + latch.getCount());
        });
        pool.submit(() -> {
            System.out.println("waiting...");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("waiting end... count = " + latch.getCount());
        });*/
    }


    /**
     * 同步等待多线程准备
     */
    public static void mutipleThreadWait() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService service = Executors.newFixedThreadPool(10,
                r -> new Thread(r, "t" + atomicInteger.getAndIncrement()));

        CountDownLatch latch = new CountDownLatch(10);
        String[] all = new String[10];
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = i;
            service.submit(() -> {
               for(int j = 0; j <= 100; j++) {
                   try {
                       Thread.sleep(random.nextInt(100));
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }

                   all[index] = Thread.currentThread().getName() + "(" + (j + "%" ) + ")";
                   System.out.print("\r" + Arrays.toString(all));
                   try {
                       Thread.sleep(2000L);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

               latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n游戏开始...");
        service.shutdown();
    }
}
