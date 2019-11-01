package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods.test.twothreadprint100;

/**
 * @author hbj
 * @date 2019/10/31 22:22
 */
public class WaitNotifyPrintOddEvenSynWaitNotify {
    private static int count;

    private static final Object lock = new Object();

    // 新建2个线程
    // 第一个只处理偶数，第二个只处理奇数（用位运算）
    // 用 synchronized 来通信
    // 使用 notify和wait 唤醒和等待
    public static void main(String[] args) throws InterruptedException {
        new Thread(new TurningRunner(), "偶数").start();
        Thread.sleep(100);
        new Thread(new TurningRunner(), "奇数").start();
    }

    static class TurningRunner implements Runnable {

        @Override
        public synchronized void run() {
            while (count <= 100) {
                synchronized (lock) {
                    // 拿到锁就打印
                    System.out.println("线程" + Thread.currentThread().getName() + ":" + count++);
                    lock.notify();
                    if (count <= 100) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
