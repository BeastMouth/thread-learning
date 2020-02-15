package com.hbj.learning.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 利用AQS实现自己的门栓（简单的线程协作器）
 *
 * @author hbj
 * @date 2020/2/15 20:53
 */
public class OneShotLatch {
    private final Sync sync = new Sync();

    public void await() {
        sync.acquireShared(0);
    }

    public void signal() {
        sync.releaseShared(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int arg) {
            // 重写方法 state == 1 代表门栓打开
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            // 重写方法
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "尝试获取latch，如果获取失败，那就等待");
                    oneShotLatch.await();
                    System.out.println(Thread.currentThread().getName() + "开闸放行，继续运行");
                }
            }).start();
        }
        Thread.sleep(5000);
        oneShotLatch.signal();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "尝试获取latch");
                    oneShotLatch.await();
                    System.out.println(Thread.currentThread().getName() + "开闸放行，继续运行");
                }
            }).start();
        }
    }
}
