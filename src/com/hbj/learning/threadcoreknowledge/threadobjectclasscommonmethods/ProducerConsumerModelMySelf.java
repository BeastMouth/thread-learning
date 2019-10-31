package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author hbj
 * @date 2019/10/31 17:39
 */
public class ProducerConsumerModelMySelf {

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        Thread thread1 = new Thread(new Producer(warehouse));
        Thread thread2 = new Thread(new Customer(warehouse));
        thread1.start();
        thread2.start();
    }
}

class Producer implements Runnable {

    private Warehouse warehouse;

    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        synchronized (warehouse) {
            while (warehouse.productQueue.size() <= 10) {
                warehouse.put();
            }
        }
    }
}

class Customer implements Runnable {

    private Warehouse warehouse;

    public Customer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        synchronized (warehouse) {
            while (warehouse.productQueue.size() >= 0) {
                warehouse.poll();
            }
        }
    }
}

class Warehouse {
    public Queue<String> productQueue = new ArrayDeque<>();

    public void put() {
        if (productQueue.size() == 10) {
            // 休息
            try {
                // 唤醒消费者
                notify();
                // 释放锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            LocalTime now = LocalTime.now();
            System.out.println("===》生产商品" + now);
            productQueue.add("商品" + now);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void poll() {
        if (productQueue.size() == 0) {
            // 休息 唤醒生产者
            try {
                // 唤醒生产者
                notify();
                // 释放锁
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("《===消费" + productQueue.poll());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}