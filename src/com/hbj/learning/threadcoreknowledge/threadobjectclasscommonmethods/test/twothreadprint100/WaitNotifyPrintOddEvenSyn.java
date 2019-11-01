package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods.test.twothreadprint100;

/**
 * @author hbj
 * @date 2019/10/31 22:22
 */
public class WaitNotifyPrintOddEvenSyn {
    private static int count;

    private static final Object lock = new Object();

    // 新建2个线程
    // 第一个只处理偶数，第二个只处理奇数（用位运算）
    // 用 synchronized 来通信
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread().getName() + ":" + count++);
                        }
                    }
                }
            }
        }, "偶数").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread().getName() + ":" + count++);
                        }
                    }
                }
            }
        }, "奇数").start();
    }
}
