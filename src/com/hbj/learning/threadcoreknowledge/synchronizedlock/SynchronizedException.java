package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * 方法抛异常后，释放锁
 * 展示不抛异常前和抛异常后的对比
 * 一旦第一个线程抛出异常，不需要手动释放锁,第二个线程会进入同步方法
 *
 * @author hbj
 * @date 2019/11/1 16:43
 */
public class SynchronizedException implements Runnable {
    static SynchronizedException synchronizedException = new SynchronizedException();

    public synchronized void method1() {
        System.out.println("我是加锁的非静态方法：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            throw new Exception();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "加锁的非静态方法运行结束");
    }

    public synchronized void method2() {
        System.out.println("我是加锁的非静态方法：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "加锁的非静态方法运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(synchronizedException);
        Thread thread2 = new Thread(synchronizedException);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("run finished");
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }
}
