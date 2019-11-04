package com.hbj.learning.threadcoreknowledge.stopthreads.volatiledemo;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 描述：用中断来修复刚才的无尽等待问题
 *
 * @author hbj
 * @date 2019/10/30 13:04
 */
public class WrongWayVolatileFixed {
    public static void main(String[] args) throws InterruptedException {
        WrongWayVolatileFixed body = new WrongWayVolatileFixed();
        ArrayBlockingQueue storage = new ArrayBlockingQueue(10);

        Producer producer = body.new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(1000);

        Consumer consumer = body.new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了。");

        producerThread.interrupt();
    }


    class Producer implements Runnable {

        BlockingQueue storage;

        public Producer(BlockingQueue storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 0;
            try {
                while (num <= 100000 && !Thread.currentThread().isInterrupted()) {
                    if (num % 100 == 0) {
                        // interrupted方法：能使线程在wait等过程中还是能够响应interrupted
                        storage.put(num);
                        System.out.println(num + "是100的倍数,被放到仓库中了。");
                    }
                    num++;
                }
            } catch (InterruptedException e) {
                // 主线程通知停止,阻塞的线程相应到主线程的停止,跳出循环,实现停止线程
                e.printStackTrace();
            } finally {
                System.out.println("生产者结束运行");
            }
        }
    }

    class Consumer {

        BlockingQueue storage;

        public Consumer(BlockingQueue storage) {
            this.storage = storage;
        }

        public boolean needMoreNums() {
            if (Math.random() > 0.95) {
                return false;
            }
            return true;
        }
    }
}
