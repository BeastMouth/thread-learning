package com.hbj.learning.uncaughtexception;

/**
 * @author hbj
 * @date 2020/3/14 5:37 下午
 */
public class UseOwnUncaughtExceptionHandler implements Runnable {

    public static void main(String[] args) {
        UseOwnUncaughtExceptionHandler r = new UseOwnUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("异常捕获器"));
        new Thread(r).start();
    }

    @Override
    public void run() {
        throw new RuntimeException("异常");
    }
}
