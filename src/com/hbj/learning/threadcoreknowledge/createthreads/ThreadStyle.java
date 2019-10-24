package com.hbj.learning.threadcoreknowledge.createthreads;

/**
 * 用Thread方式实现线程
 *
 * @author hbj
 * @date 2019/10/24 14:22
 */
public class ThreadStyle extends Thread {
    @Override
    public void run() {
        System.out.println("使用Thread方式实现线程");
    }

    public static void main(String[] args) {
        new ThreadStyle().start();
    }
}
