package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * synchronized对象锁：代码块锁
 * this或自建一个对象
 *
 * @author hbj
 * @date 2019/11/1 15:03
 */
public class SynchronizedObjectCodeBlock implements Runnable {

    static SynchronizedObjectCodeBlock instance = new SynchronizedObjectCodeBlock();
    Object lock1 = new Object();
    Object lock2 = new Object();

    @Override
    public void run() {
//        synchronized (this) {
        synchronized (lock1) {
            System.out.println("我是lock1对象锁的代码块形式" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "lock1部分运行结束");
        }

//        synchronized (lock1) {
//            System.out.println("我是lock2对象锁的代码块形式" + Thread.currentThread().getName());
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() + "lock2部分运行结束");
//        }
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
