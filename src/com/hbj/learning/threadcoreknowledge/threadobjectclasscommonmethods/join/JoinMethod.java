package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods.join;

/**
 * @author hbj
 * @date 2019/11/4 20:49
 */
public class JoinMethod {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            }
        });

        thread1.start();
        thread2.start();
        System.out.println("开始等待子线程执行");
        thread1.join();
        thread2.join();
        System.out.println("子线程执行完毕");
    }
}
