package com.hbj.learning.threadlocal;

/**
 * @author hbj
 * @date 2020/4/11 3:57 下午
 */
public class InheritableThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        // 可用于父子线程ThreadLocal传值
        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("aaaa");
        System.out.println("父线程的值：" + threadLocal.get());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程的值：" + threadLocal.get());
            }
        }).start();
        Thread.sleep(2000);
    }
}
