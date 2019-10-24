package com.hbj.learning.threadcoreknowledge.createthreads.wrongways;

/**
 * 匿名内部类创建线程的两种方法 本质还是Runnable
 *
 * @author hbj
 * @date 2019/10/24 17:48
 */
public class AnonymousInnerClassDemo {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
