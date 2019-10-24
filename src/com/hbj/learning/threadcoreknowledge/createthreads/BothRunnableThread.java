package com.hbj.learning.threadcoreknowledge.createthreads;

/**
 * 同时使用Runnable和Thread方法创建线程
 *
 * @author hbj
 * @date 2019/10/24 15:31
 */
public class BothRunnableThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            // 传入Runnable对象
            @Override
            public void run() {
                System.out.println("我来自Runnable");
            }
        }) {
            // 重写了Thread类的run方法
            // 覆盖了原来的三行代码，导致上面的Runnable传参失效
            //    @Override
            //    public void run() {
            //        if (target != null) {
            //            target.run();
            //        }
            //    }
            @Override
            public void run() {
                System.out.println("我来自Thread");
            }
        }.start();
    }
}
