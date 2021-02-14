package com.mutithread.juc;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StampedLockTest {

    public static void main(String[] args) throws InterruptedException {
        DataContrainerStamped stamped = new DataContrainerStamped(5);

        new Thread(() -> {
            try {
                // System.out.println("读取数据为： " + stamped.read(1));
                log.info("读取数据为： {}", stamped.read(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        Thread.sleep(500);
        /*new Thread(() -> {
            try {
                stamped.read(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();*/
        new Thread(() -> {
            try {
                stamped.write(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
