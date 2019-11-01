package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * synchronized对象锁：代码块锁
 * this或自建一个对象
 *
 * @author hbj
 * @date 2019/11/1 15:03
 */
public class SynchronizedObjectMethod implements Runnable {

    static SynchronizedObjectMethod instance = new SynchronizedObjectMethod();
//    static SynchronizedObjectMethod instance2 = new SynchronizedObjectMethod();

    @Override
    public void run() {
        commonMethod();
    }

    public synchronized void commonMethod() {
        System.out.println("我是对象锁的方法修饰符形式：" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
//        Thread thread2 = new Thread(instance2);
        thread1.start();
        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) {

        }
        System.out.println("run finished");
    }
}
