package com.hbj.learning.threadcoreknowledge.synchronizedlock.reenter;

/**
 * 可重入粒度测试：证明同一个方法可重入
 *
 * @author hbj
 * @date 2019/11/1 17:14
 */
public class SynchronizedOneMethodReEnter {
    int i = 0;

    public synchronized void method() {
        System.out.println("成功进入方法，i = " + i);
        if (i < 3) {
            i++;
            method();
        }
    }

    public static void main(String[] args) {
        SynchronizedOneMethodReEnter synchronizedOneMethodReEnter = new SynchronizedOneMethodReEnter();
        synchronizedOneMethodReEnter.method();
    }
}
