package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.concurrent.TimeUnit;

/**
 * sleep抛异常会释放synchronized锁
 *
 * @author hbj
 * @date 2019/11/4 19:10
 */
public class SleepExceptionCanReleaseLock implements Runnable {
    @Override
    public synchronized void run() {
        System.out.println("我获取到了锁");
        try {
            TimeUnit.SECONDS.sleep(10);
            throw new RuntimeException("我抛异常啦");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我释放了锁");
    }

    public static void main(String[] args) {
        SleepExceptionCanReleaseLock sleepExceptionCanReleaseLock = new SleepExceptionCanReleaseLock();
        new Thread(sleepExceptionCanReleaseLock).start();
        new Thread(sleepExceptionCanReleaseLock).start();

    }
}
