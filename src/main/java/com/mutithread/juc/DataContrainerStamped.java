package com.mutithread.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

/**
 * jdk8中引入 StampedLock
 *      不可重入锁
 *      ReentrantReadWriteLock 的一个问题：如果有读线程，那么写线程需要阻塞 这是一种悲观锁
 *      StampedLock 在读的时候允许获取写锁后写入，这样导致读的数据有可能不一致，就需要额外的代码来判断读的时候是否需要写入 这是一种乐观锁
 *
 *      // 加读锁  悲观读
 *      long stamp = lock.readLock();
 *      lock.unlockRead(stamp);
 *
 *      // 加写锁
 *      long stamp = lock.writeLock();
 *      lock.unlockWrite(stamp);
 *
 *      // 乐观读锁
 *      long stamp = lock.tryOptimisticRead();
 *      if (!lock.validate(stamp)) { // 验戳
 *          // 锁升级
 *      }
 *
 */
@Slf4j
public class DataContrainerStamped {
    private int data;
    private StampedLock lock = new StampedLock();

    public DataContrainerStamped(int data) {
        this.data = data;
    }

    public int read(int readTime) throws InterruptedException {

        long stamp = lock.tryOptimisticRead();   // 乐观读
        // System.out.println("乐观读锁 stamp：" + stamp);
        log.info("乐观读锁 stamp：{}", stamp);
        Thread.sleep(readTime*1000L);

        if (lock.validate(stamp)) {
            // System.out.println("读锁完成, data: " + data + ", stamp: " + stamp);
            log.info("读锁完成, data: {}, stamp: {}", data, stamp);
            return data;
        }

        // 验戳没有成功  要重新读取   锁升级 -- 读锁
        // System.out.println("升级成读锁...");
        log.info("升级成读锁...");
        try {
            stamp = lock.readLock();   // 悲观读
            // System.out.println("读锁获取 stamp: " + stamp);
            log.info("读锁获取 stamp: {}", stamp);
            Thread.sleep(readTime*1000L);
            return data;

        } finally {
            // System.out.println("读锁解锁 stamp：" + stamp + ", data: " + data);
            log.info("读锁解锁 stamp：{}, data: {}", stamp, data);
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) throws InterruptedException {
        long stamp = lock.writeLock();
        // System.out.println("写锁 stamp: " + stamp);
        log.info("写锁 stamp: {}", stamp);
        try {
            Thread.sleep(2000L);
            this.data = newData;
        } finally {
            // System.out.println("写锁解锁 stamp：" + stamp);
            log.info("写锁解锁 stamp：{}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
