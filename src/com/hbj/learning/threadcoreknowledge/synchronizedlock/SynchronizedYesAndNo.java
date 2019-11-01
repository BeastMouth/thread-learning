package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * 两个线程同时访问同步方法和非同步方法
 *
 * @author hbj
 * @date 2019/11/1 16:27
 */
public class SynchronizedYesAndNo implements Runnable {

    static SynchronizedYesAndNo instance = new SynchronizedYesAndNo();

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            methodSynchronized();
        } else {
            methodNoSynchronized();
        }
    }

    public synchronized void methodSynchronized() {
        System.out.println("我是加锁的方法：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "加锁的方法运行结束");
    }

    public void methodNoSynchronized() {
        System.out.println("我是不加锁的方法：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "不加锁的方法运行结束");
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
