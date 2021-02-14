package com.mutithread.diythreadpool;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DIY 一个阻塞队列
 * @param <T>
 */
public class BlockingQueue<T> {
    // 队列
    private Deque<T> queue = new ArrayDeque<>();
    // 锁
    private ReentrantLock lock = new ReentrantLock();
    // 生产者条件变量
    private Condition fullWaitSet = lock.newCondition();
    // 消费者条件变量
    private Condition emptyWaitSet = lock.newCondition();
    // 容量
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 阻塞添加
    public void put(T task) {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                try {
                    System.out.println("等待添加任务....");
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("添加任务...");
            queue.addLast(task);
            emptyWaitSet.signalAll();
        }finally {
            lock.unlock();
        }
    }

    // 带有超时的添加
    public boolean offer(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.size() == capacity) {
                try {
                    if (nanos <= 0) {
                        return false;
                    }
                    System.out.println("等待添加任务....");
                    nanos = fullWaitSet.awaitNanos(nanos);  // 返回剩余的时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("添加任务...");
            queue.addLast(task);
            emptyWaitSet.signalAll();
            return true;
        }finally {
            lock.unlock();
        }
    }

    // 带有拒绝策略的添加
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            // 如果 队列满了  执行拒绝策略
            if (queue.size() == capacity) {
                rejectPolicy.reject(this, task);
            }else {
                System.out.println("添加到任务队列...");
                queue.addLast(task);
                emptyWaitSet.signalAll();
            }
        }finally {
            lock.unlock();
        }
    }

    // 阻塞获取
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T task = queue.removeFirst();  // 从队列中删除
            fullWaitSet.signalAll();
            return task;
        }finally {
            lock.unlock();
        }
    }


    // 带有超时消费
    public T poll(long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            // 统一转为纳秒
            long nanos = timeUnit.toNanos(timeout);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = emptyWaitSet.awaitNanos(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T task = queue.removeFirst(); // 从队列中删除
            fullWaitSet.signalAll();
            return task;
        }finally {
            lock.unlock();
        }
    }

    // 获取队列中的线程数
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }

}
