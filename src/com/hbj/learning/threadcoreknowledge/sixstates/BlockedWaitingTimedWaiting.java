package com.hbj.learning.threadcoreknowledge.sixstates;

/**
 * @author hbj
 * @date 2019/10/31 11:29
 */
public class BlockedWaitingTimedWaiting implements Runnable {
    public static void main(String[] args) {
        BlockedWaitingTimedWaiting runnable = new BlockedWaitingTimedWaiting();
        Thread thread1 = new Thread(runnable);
        thread1.start();
        Thread thread2 = new Thread(runnable);
        thread2.start();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印出Timed_Waiting状态，因为正在执行Thread.sleep(1000);
        System.out.println(thread1.getState());
        // 打印出BLOCKED状态，因为thread2想拿得到sync()的锁却拿不到
        System.out.println(thread2.getState());
        try {
            Thread.sleep(1300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打印出WAITING状态，因为执行了wait()
        System.out.println(thread1.getState());
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "启动了");
        syn();
    }

    private synchronized void syn() {
        try {
            Thread.sleep(1000);
            // 进入wait会释放锁（monitor锁）（允许其他线程来执行这个synchronized代码块）
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
