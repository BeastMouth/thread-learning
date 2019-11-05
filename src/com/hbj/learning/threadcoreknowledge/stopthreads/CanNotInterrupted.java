package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * @author hbj
 * @date 2019/10/29 21:57
 */
public class CanNotInterrupted {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
                if (num % 100 == 0) {
                    System.out.println(num + "是100的倍数");
                }
                num++;
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    // 一旦响应过中断后，就会将interrupted标记位清楚
                    System.out.println("未在while处中断");
//                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        while (true) {
            thread.interrupt();
        }
    }
}
