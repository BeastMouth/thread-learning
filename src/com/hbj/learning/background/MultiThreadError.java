package com.hbj.learning.background;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 运行结果出错
 * 演示技术不准确（结果减少），找出具体出错的位置
 *
 * @author hbj
 * @date 2019/11/5 14:35
 */
public class MultiThreadError implements Runnable {
    int index = 0;
    final boolean[] marked = new boolean[100000];

    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();
    static MultiThreadError instance = new MultiThreadError();
    static CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);
    static CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2);

    @Override
    public void run() {
//        while (index < 10000) {
//            index++;
//        }
        marked[0] = true;
        for (int i = 0; i < 10000; i++) {
            try {
                cyclicBarrier2.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            // （三步  获取，+1），写入，所以会出现结果小于20000
            index++;
            try {
                cyclicBarrier1.reset();
                cyclicBarrier2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (instance) {
                // synchronized可见性，一个线程改变的数据能够被另一个线程感知到
                if (marked[index] && marked[index - 1]) {
                    System.out.println("在 " + index + " 发生了冲突");
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面上的结果" + instance.index);
        System.out.println("真正运行的次数" + realIndex.get());
        System.out.println("错误的次数" + wrongCount.get());
    }
}
