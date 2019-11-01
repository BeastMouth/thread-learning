package com.hbj.learning.threadcoreknowledge.synchronizedlock.reenter;

/**
 * 可重入粒度测试：证明不同方法可重入
 *
 * @author hbj
 * @date 2019/11/1 17:16
 */
public class SynchronizedDifferentMethod {
    private synchronized void method1() {
        System.out.println("I am method 1");
        method2();
    }

    private synchronized void method2() {
        System.out.println("I am method 2");
    }

    public static void main(String[] args) {
        SynchronizedDifferentMethod synchronizedDifferentMethod = new SynchronizedDifferentMethod();
        synchronizedDifferentMethod.method1();
    }
}
