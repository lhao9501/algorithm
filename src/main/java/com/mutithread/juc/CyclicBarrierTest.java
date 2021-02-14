package com.mutithread.juc;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(2);  // 人满发车

        new Thread(() -> {
            System.out.println("t1开始... " + new Date());
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("t1向下继续... " + new Date());
        }, "t1").start();

        new Thread(() -> {
            System.out.println("t2开始... " + new Date());
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("t2向下继续... " + new Date());
        }, "t2").start();
    }
}
