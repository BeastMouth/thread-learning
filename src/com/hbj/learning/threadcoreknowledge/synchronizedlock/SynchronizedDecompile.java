package com.hbj.learning.threadcoreknowledge.synchronizedlock;

/**
 * synchronized 反编译例子
 *
 * @author hbj
 * @date 2019/11/4 11:58
 */
public class SynchronizedDecompile {
    private Object object = new Object();

    public void method1(Thread thread) {
        synchronized (object) {

        }
    }
}
