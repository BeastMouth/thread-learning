package com.hbj.learning.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 用ThreadMXBean检测死锁
 *
 * @author hbj
 * @date 2020/1/11 17:30
 */
public class ThreadMXBeanDetection implements Runnable {
    static Object lock1 = new Object();
    static Object lock2 = new Object();
    int flag = 1;

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (lock1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println("线程1成功拿到锁");
                }
            }
        }
        if (flag == 0) {
            synchronized (lock2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println("线程2成功拿到锁");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBeanDetection r1 = new ThreadMXBeanDetection();
        ThreadMXBeanDetection r2 = new ThreadMXBeanDetection();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        Thread.sleep(1000);
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 线程id
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (long deadlockedThread : deadlockedThreads) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThread);
                System.out.println("发现死锁啦！" + threadInfo.getThreadName());
            }
        }
    }
}
