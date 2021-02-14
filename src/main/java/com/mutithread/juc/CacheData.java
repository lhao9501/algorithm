package com.mutithread.juc;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock
 *      读锁不支持条件变量
 *      重入时不支持升级(持有读锁不能获取写锁)，支持降级(持有写锁可获取读锁)
 *      读读并发 读写不并发
 */
public class CacheData {
    private Object data;
    private volatile boolean cacheValid;
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void processCachedData() {
        // 先获取读锁
        rwl.readLock().lock();
        if (!cacheValid) {
            // 获取写锁前 释放读锁
            rwl.readLock().unlock();

            rwl.writeLock().lock();
            try {
                // 释放读锁 加上写锁的空隙中  有可能被改变 所以这里再次判断
                if (!cacheValid) {
                    data = new Object();
                    cacheValid = true;
                }

                // 降级为读锁  释放写锁 这样能让其他线程读取缓存
                // 如果不获取读锁而是直接释放写锁  那么数据在释放写锁后有可能被更改
                rwl.readLock().lock();
            } finally {
                rwl.writeLock().unlock();
            }
        }

        try {
            // 应用缓存
            // use(data);
        } finally {
            rwl.readLock().unlock();
        }
    }

}
