package com.mutithread.diythreadpool;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * DIY 一个线程池
 */
public class DIYThreadPool {
    // 阻塞队列
    private BlockingQueue<Runnable> taskQueue;
    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();
    // 核心线程数
    private int coreSize;
    // 超时
    private long timeout;
    private TimeUnit timeUnit;
    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;


    public DIYThreadPool(int queueCapacity, int coreSize, long timeout, TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy) {
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    // 执行任务
    public void execute(Runnable task) {
        // 如果没有超过coreSize核心数  交给work执行
        // 如果超过coreSize核心数  加入队列暂存
        synchronized (workers) {
            if (workers.size() < coreSize) {
                // 创建核心线程
                Worker worker = new Worker(task);
                System.out.println("新增work...");
                workers.add(worker);
                worker.start();
            }else {
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }


    /**
     * 对执行的任务再包裹一层
     */
    public class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (null != task || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    System.out.println("任务正在执行...." + task);
                    task.run();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    task = null;
                }
            }

            // 当 当前线程执行完并且队列中没有任务
            synchronized (workers) {
                System.out.println("线程被移除...");
                workers.remove(this);
            }
        }
    }
}
