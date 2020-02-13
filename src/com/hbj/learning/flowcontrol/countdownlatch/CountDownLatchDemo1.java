package com.hbj.learning.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 工厂中，质检，五个工人检查，所有人都认为通过后，才通过
 * 一等多
 *
 * @author hbj
 * @date 2020/2/13 20:52
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService executors = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + no + "完成了检查");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        latch.countDown();
                    }
                }
            };
            executors.submit(runnable);
        }
        System.out.println("等待五个人检查完....");
        latch.await();
        System.out.println("所有人都完成了工作，进入下一个环节");
        executors.shutdown();
    }
}
