package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * @author hbj
 * @date 2019/10/30 16:56
 */
public class RightWayInterrupted {
    public static void main(String[] args) throws InterruptedException {

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                }
            }
        });

        // 启动线程
        threadOne.start();
        // 设置中断标志
        threadOne.interrupt();
        // 获取中断标志
        System.out.println("isInterrupted: " + threadOne.isInterrupted());
        // 获取中断标志并重置 (获取当前的执行线程，虽然是调用threadOne的interrupted，但是当前的执行线程是main)
        System.out.println("isInterrupted: " + threadOne.interrupted());
        // 获取中断标志并重直（也是获取当前的执行线程）
        System.out.println("isInterrupted: " + Thread.interrupted());
        // 获取中断标志
        System.out.println("isInterrupted: " + threadOne.isInterrupted());
        threadOne.join();
        System.out.println("Main thread is over.");
    }
}
