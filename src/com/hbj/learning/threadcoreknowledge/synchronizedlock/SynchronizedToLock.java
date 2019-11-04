package com.hbj.learning.threadcoreknowledge.synchronizedlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hbj
 * @date 2019/11/4 11:05
 */
public class SynchronizedToLock {

    Lock lock = new ReentrantLock();

    public synchronized void method1() {
        System.out.println("我是synchronized类型的方法");
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("我是等价替代的Lock类型的方法");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SynchronizedToLock synchronizedToLock = new SynchronizedToLock();
        synchronizedToLock.method1();
        synchronizedToLock.method2();
    }
}
