package com.hbj.learning.deadlock.avoiddeadlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用tryLock来避免死锁
 *
 * @author hbj
 * @date 2020/1/11 18:57
 */
public class TryLockDeadLock implements Runnable {
    int flag = 1;
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        TryLockDeadLock r1 = new TryLockDeadLock();
        TryLockDeadLock r2 = new TryLockDeadLock();
        r1.flag = 1;
        r2.flag = 0;
        new Thread(r1).start();
        new Thread(r2).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁1");
                        Thread.sleep(new Random().nextInt(1000));
                        if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                            System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁2");
                            System.out.println("线程" + Thread.currentThread().getName() + "成功获取到两把锁");
                            lock2.unlock();
                            lock1.unlock();
                            break;
                        } else {
                            System.out.println("线程" + Thread.currentThread().getName() + "获取锁2失败，已重试");
                            lock1.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程" + Thread.currentThread().getName() + "获取锁1失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁2");
                        Thread.sleep(new Random().nextInt(1000));
                        if (lock1.tryLock(3000, TimeUnit.MILLISECONDS)) {
                            System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁1");
                            System.out.println("线程" + Thread.currentThread().getName() + "成功获取到两把锁");
                            lock1.unlock();
                            lock2.unlock();
                            break;
                        } else {
                            System.out.println("线程" + Thread.currentThread().getName() + "获取锁1失败，已重试");
                            lock2.unlock();
                            Thread.sleep(new Random().nextInt(1000));
                        }
                    } else {
                        System.out.println("线程" + Thread.currentThread().getName() + "获取锁2失败，已重试");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
