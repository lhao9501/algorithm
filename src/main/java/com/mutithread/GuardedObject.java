package com.mutithread;

/**
 * Guarded Suspension
 */
public class GuardedObject {
    // 响应信息
    private Object response;
    // 标识
    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object get(long millis) {
        // 开始计时
        long begin = System.currentTimeMillis();
        // 等待时间
        long waitTime = 0;
        synchronized(this) {
            while (null == response) {
                long delay = millis - waitTime;
                if (delay <= 0) break;   // 如果等待时间超过规定时间  退出循环
                try {
                    wait(delay);  // 防止虚假唤醒  用差值delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                waitTime = System.currentTimeMillis() - begin;
            }

            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            notifyAll();
            this.response = response;
        }
    }
}
