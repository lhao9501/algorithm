package com.othertest;

public class StopThread extends Thread {

    @Override
    public void run() {
        int count = 0;
        while (!Thread.currentThread().isInterrupted() && count < 1000) {
            System.out.println("count= " + count++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            /*try {
                while (!Thread.currentThread().isInterrupted() && num <= 1000) {
                    System.out.println(num++);
                    Thread.sleep(1000000);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(Thread.currentThread().isInterrupted());  // 抛出异常  清空标志位
            }*/

            while (!Thread.currentThread().isInterrupted() && num <= 1000) {
                try {
                    System.out.println(num++);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().isInterrupted());
                    //Thread.currentThread().interrupt();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5);
        thread.interrupt();

    }
}
