package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * 如果在执行过程中每次循环都会有sleep或wait等方法时
 * 则不需要每次迭代不需要去检测有没有中断，因为sleep中会帮我们去响应这个中断
 *
 * @author hbj
 * @date 2019/10/29 21:37
 */
public class RightWayStopThreadWithSleepEveryLoop {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            try {
                while (num <= 10000) {
                    if (num % 100 == 0) {
                        System.out.println(num + "是100的倍数");
                    }
                    num++;
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
