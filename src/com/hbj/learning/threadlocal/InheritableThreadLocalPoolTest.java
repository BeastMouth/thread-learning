package com.hbj.learning.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hbj
 * @date 2020/4/11 4:14 下午
 */
public class InheritableThreadLocalPoolTest {
    public static void main(String[] args) throws InterruptedException {
        InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
        threadLocal.set("aaaa");
        System.out.println("父线程的值：" + threadLocal.get());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("============= start =============");
                System.out.println("当前的线程名：" + Thread.currentThread().getName());
                System.out.println("父线程的值：" + threadLocal.get());
                System.out.println("子线程修改父线程的值为bbbb");
                threadLocal.set("bbbb");
                System.out.println("子线程的值：" + threadLocal.get());
                System.out.println("============== end ==============");
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 20; i++) {
            executorService.submit(runnable);
//            TimeUnit.SECONDS.sleep(5);
            System.out.println("主线程中父线程的值为：" + threadLocal.get());
        }
    }
}
