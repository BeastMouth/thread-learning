package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * 同时访问一个类不同的同步方法
 *
 * @author hbj
 * @date 2019/11/1 16:36
 */
public class SynchronizedDifferentMethod implements Runnable {

    static SynchronizedDifferentMethod instance = new SynchronizedDifferentMethod();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            methodSynchronizedOne();
        } else {
            methodSynchronizedTwo();
        }
    }

    public synchronized void methodSynchronizedOne() {
        System.out.println("我是加锁的方法1：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "加锁的方法1运行结束");
    }

    public synchronized void methodSynchronizedTwo() {
        System.out.println("我是加锁的方法2：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "加锁的方法2运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("run finished");
    }
}
