package com.hbj.learning.lock.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hbj
 * @date 2020/1/24 13:52
 */
public class TryLockDeadLock implements Runnable {
    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    @Override
    public void run() {
//        try {
//            lock1.lock();
//            Thread.sleep(5000);
//            System.out.println("获取到锁1");
//        } catch (InterruptedException e) {
//            System.out.println("睡眠期间被中断");
//        } finally {
//            lock1.unlock();
//            System.out.println("释放了锁1");
//        }
//        if (lock1.tryLock()) {
//            try {
//                System.out.println("获取到锁1");
//            } finally {
//                lock1.unlock();
//                System.out.println("释放了锁1");
//            }
//        } else {
//            System.out.println("没有获取到锁1");
//        }

        try {
            System.out.println(Thread.currentThread().getName() + "尝试获取锁1");
            lock1.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + "获取到锁1");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "睡眠期间被中断");
            } finally {
                lock1.unlock();
                System.out.println(Thread.currentThread().getName() + "释放了锁1");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "尝试获取锁期间被中断");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TryLockDeadLock r1 = new TryLockDeadLock();
        Thread thread0 = new Thread(r1);
        Thread thread1 = new Thread(r1);
        thread0.start();
        thread1.start();
        Thread.sleep(200);
        thread0.interrupt();
    }
}
