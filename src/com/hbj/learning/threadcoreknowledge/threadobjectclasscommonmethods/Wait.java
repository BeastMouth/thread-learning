package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 展示wait和notify的基本用法
 * 1. 研究代码执行顺序
 * 2. 证明wait释放锁
 *
 * @author hbj
 * @date 2019/10/31 16:48
 */
public class Wait {
    public static Object object = new Object();

    static class Thread1 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                System.out.println("线程" + Thread.currentThread().getName() + "开始执行了");
                try {
                    // 等待唤醒 释放锁
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁。");
            }
        }
    }

    static class Thread2 extends Thread {

        @Override
        public void run() {
            synchronized (object) {
                object.notify();
                System.out.println("线程" + Thread.currentThread().getName() + "调用了notify()");
                // 整个代码块走完了 才会释放出object锁，线程一才会开始继续执行
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        // 保证线程1先执行，线程2后执行
        thread1.start();
        Thread.sleep(200);
        thread2.start();
    }
}
