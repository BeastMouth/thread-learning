package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * @author hbj
 * @date 2019/11/4 17:53
 */
public class SleepDontReleaseMonitor implements Runnable {

    @Override
    public void run() {
        methodOne();
    }

    private synchronized void methodOne() {
        System.out.println("线程" + Thread.currentThread().getName() + "获取到了monitor");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程" + Thread.currentThread().getName() + "退出了同步代码块");
    }

    public static void main(String[] args) {
        SleepDontReleaseMonitor sleepDontReleaseMonitor = new SleepDontReleaseMonitor();
        new Thread(sleepDontReleaseMonitor).start();
        new Thread(sleepDontReleaseMonitor).start();
    }
}
