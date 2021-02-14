package com.mutithread.diythreadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainTest {
    public static void main(String[] args) {
        DIYThreadPool pool = new DIYThreadPool(3, 2, 1000, TimeUnit.MILLISECONDS, (queue, task) -> {
//            System.out.println("放弃");
            System.out.println("拒绝策略");
            // 死等
//            queue.put(task);
            // 调用者自己执行
            task.run();

            System.out.println("当前线程：" + Thread.currentThread().getName());
        });

        for (int i = 0; i < 6; i++) {
            int j = i;
            pool.execute(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(j);
            });
        }


        /**
         * 各种线程池 练习
         *
         * */
        // 固定线程的线程池  核心线程数=最大线程数  LinkedBlockingQueue
        Executors.newFixedThreadPool(2, r -> new Thread(() -> {}));

        // 可缓存线程池  最大线程数为Integer.MAX_VALUE  SynchronousQueue
        Executors.newCachedThreadPool();

        // 定时或者周期执行任务
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        // 2s后执行一次后结束
        service.schedule(() -> {}, 2, TimeUnit.SECONDS);
        // 以固定频率执行 initialDelay为第一次延迟时间  period周期 第一次延迟后 每次延迟period后执行
        service.scheduleAtFixedRate(() -> {}, 2, 2, TimeUnit.SECONDS);
        // 周期性执行 以任务的结束时间为下一次的延迟开始时间
        service.scheduleAtFixedRate(() -> {}, 2, 2, TimeUnit.SECONDS);

        // 用唯一的线程执行任务 (如果抛出异常 会重新创建线程执行)  适合顺序提交依次执行的场景
        Executors.newSingleThreadExecutor();

        //
        Executors.newSingleThreadScheduledExecutor();
    }
}
