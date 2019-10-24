package com.hbj.learning.threadcoreknowledge.createthreads.wrongways;

/**
 * 使用lambda创建线程-》只是写法不同
 *
 * @author hbj
 * @date 2019/10/24 17:50
 */
public class LambdaDemo {
    public static void main(String[] args) {
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
    }
}
