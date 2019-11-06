package com.hbj.learning.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 演示重排序现象
 * 因为重排序不是每次都出现的，所以要不断重试，直到达到某个条件才停止（可以用于测试小概率时间）
 *
 * @author hbj
 * @date 2019/11/6 21:52
 */
public class OutOfOrderExecution {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;

            CountDownLatch latch = new CountDownLatch(3);

            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a = 1;
                    x = b;
                }
            });

            Thread two = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.countDown();
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    b = 1;
                    y = a;
                }
            });

            one.start();
            two.start();

            latch.countDown();
            one.join();
            two.join();

            System.out.println("执行的第" + i + "次");
            System.out.println("x = " + x + ", y = " + y);
            if (x == 0 && y == 0) {
                break;
            }
        }

    }
}
