package com.hbj.learning.threadlocal;

/**
 * @author hbj
 * @date 2020/1/17 17:51
 */
public class ThreadLocalNPE {
    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

//    public long get() {
    // 如果为long,会尝试装箱拆箱
//        return longThreadLocal.get();
//    }

    public Long get() {
        return longThreadLocal.get();
    }

    public static void main(String[] args) {
        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        System.out.println(threadLocalNPE.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocalNPE.set();
                System.out.println(threadLocalNPE.get());
            }
        }).start();
    }
}
