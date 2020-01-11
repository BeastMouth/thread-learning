package com.hbj.learning.volatilesample;

/**
 * @author hbj
 * @date 2020/1/2 17:43
 */
public class VolatileTest implements Runnable {
    volatile int a = 0;
    volatile int b = 0;
    volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Runnable r = new VolatileTest();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("a : " + ((VolatileTest) r).a);
        System.out.println("b : " + ((VolatileTest) r).b);
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (flag) {
                a++;
            } else {
                b++;
            }
            flag = !flag;
        }

    }
}
