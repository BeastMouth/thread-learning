package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * 最佳实现：catch了InterruptedException后优先选择在方法签名中抛出异常
 * 那么run()就会强制try/catch
 *
 * @author hbj
 * @date 2019/10/29 22:05
 */
public class RightWayStopThreadInProd implements Runnable {

    @Override
    public void run() {
        while (true && !Thread.currentThread().isInterrupted()) {
            System.out.println("go");
            try {
                throwInMethod2();
            } catch (InterruptedException e) {
                // 保存日志，停止程序
                System.out.println("保存日志操作");
                e.printStackTrace();
            }
        }
    }

    private void throwInMethod() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 提供给别人调用的方法，应该将异常抛出而不是自己内部处理（否则调用方不能正确的相应中断请求）
    private void throwInMethod2() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProd());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
