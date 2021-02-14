package com.mutithread.juc;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class FutureDemo {

    public static final int SELEEP_GAP = 500;

    public static String getCurrentThreadName() {
        return Thread.currentThread().getName();
    }

    public static void drinkTea(boolean hotWater, boolean wash) {
        if (hotWater && wash) {
            // System.out.println("泡茶喝...");
            log.info("泡茶喝....");
        } else if (!wash) {
            // System.out.println("清洗出问题，不能喝茶...");
            log.info("清洗出问题，不能喝茶...");
        } else if (!hotWater) {
            // System.out.println("烧水出问题，不能喝茶...");
            log.info("烧水出问题，不能喝茶...");
        }
    }



    static class HotWaterJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                // System.out.println("洗好水壶，放入凉水，放在火上...");
                log.info("洗好水壶，放入凉水，放在火上...");
                Thread.sleep(SELEEP_GAP);
                // System.out.println("水烧开了...");
                log.info("水烧开了...");
            } catch (InterruptedException e) {
                // System.out.println("烧水过程中发生中断...");
                log.info("烧水过程中发生中断...");
                return false;
            }
            // System.out.println("烧水运行结束...");
            log.info("烧水运行结束...");
            return true;
        }
    }


    static class WashJob implements Callable<Boolean> {
        @Override
        public Boolean call() throws Exception {
            try {
                // System.out.println("洗茶壶，洗茶杯，拿茶叶...");
                log.info("洗茶壶，洗茶杯，拿茶叶...");
                Thread.sleep(SELEEP_GAP);
                // System.out.println("洗完了...");
                log.info("洗完了...");
            } catch (InterruptedException e) {
                // System.out.println("清洗过程中发生中断...");
                log.info("清洗过程中发生中断...");
                return false;
            }
            // System.out.println("清洗运行结束...");
            log.info("清洗运行结束...");
            return true;
        }
    }


    public static void main(String[] args) {
        Callable<Boolean> washJob = new WashJob();
        FutureTask<Boolean> wt = new FutureTask<>(washJob);   // Callable 和 Thread 的中间挑梁
        Thread wthread = new Thread(wt, "清洗-thread");
        Callable<Boolean> hotWaterJob = new HotWaterJob();
        FutureTask<Boolean> ht = new FutureTask<>(hotWaterJob);
        Thread hthread = new Thread(ht, "烧水-thread");

        try {
            wthread.start();

            Thread.sleep(2000L);
            hthread.start();

            Thread.currentThread().setName("主线程-main");

            boolean wOk = wt.get();   // 得到结果
            boolean hOk = ht.get();

            drinkTea(hOk, wOk);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // System.out.println("运行结束....");
        log.info("运行结束....");
    }
}
