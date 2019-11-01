package com.hbj.learning.threadcoreknowledge.synchronizedlock.reenter;

/**
 * 可重入粒度测试：证明可重入不要求是同一个类中的
 *
 * @author hbj
 * @date 2019/11/1 17:21
 */
public class SynchronizedSuperClass {
    public synchronized void method() {
        System.out.println("I am super class");
    }
}

class SynchronizedKidClass extends SynchronizedSuperClass {
    @Override
    public synchronized void method() {
        System.out.println("I am kid class");
        super.method();
    }

    public static void main(String[] args) {
        SynchronizedKidClass synchronizedKidClass = new SynchronizedKidClass();
        synchronizedKidClass.method();
    }
}
