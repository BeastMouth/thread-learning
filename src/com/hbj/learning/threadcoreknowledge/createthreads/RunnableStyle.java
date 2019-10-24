package com.hbj.learning.threadcoreknowledge.createthreads;

/**
 * 用Runnable方式创建线程(使用该方法更好)
 *
 * @author hbj
 * @date 2019/10/24 14:19
 */
public class RunnableStyle implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new RunnableStyle());
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("使用Runnable方法实现线程");
    }
}
