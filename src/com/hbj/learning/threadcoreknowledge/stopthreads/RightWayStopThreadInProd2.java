package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * 最佳实现2：在catch语句中调用Thread.currentThread().interrupt()来恢复设置中断的状态
 * 以便在后续的执行中，依然能够检查到刚才发生了中断
 * 回到刚才的RightWayStopThreadInProd中来补上中断，让它跳出
 *
 * @author hbj
 * @date 2019/10/29 22:05
 */
public class RightWayStopThreadInProd2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted 程序运行结束");
                break;
            }
            System.out.println("go");
            reInterrupted();
            System.out.println("保存日志操作");
        }
    }

    private void reInterrupted() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
