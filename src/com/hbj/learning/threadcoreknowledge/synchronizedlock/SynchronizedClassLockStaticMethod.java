package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * 类锁的第一种形式：synchronized修饰static方法
 *
 * @author hbj
 * @date 2019/11/1 15:48
 */
public class SynchronizedClassLockStaticMethod implements Runnable {

    static SynchronizedClassLockStaticMethod instance1 = new SynchronizedClassLockStaticMethod();
    static SynchronizedClassLockStaticMethod instance2 = new SynchronizedClassLockStaticMethod();

    @Override
    public void run() {
        staticMethod();
    }

    public static synchronized void staticMethod() {
        System.out.println("我是类锁的第一种形式-static形式：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("run finished");
    }
}
