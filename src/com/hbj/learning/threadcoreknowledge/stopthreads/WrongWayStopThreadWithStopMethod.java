package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * 错误的停止方法：用stop()来停止线程，会导致线程运行一半突然停止，没办法完成一个基本单位的操作（一个连队），会造成脏数据（有的连队多领取少领取装备）。
 * 例如银行转账 把十笔转账作为一个基本操作，就很容易出现漏转的情况（该问题不宜排查）
 * stop也会释放掉monitor的锁（官网介绍：stop本质上是不安全的（弃用stop的原因）。同时停止线程会导致它解锁已锁定的所有监视器）
 *
 * @author hbj
 * @date 2019/10/29 22:58
 */
public class WrongWayStopThreadWithStopMethod implements Runnable {

    @Override
    public void run() {
        // 模拟10个连队，每个连队10人领取武器
        for (int i = 1; i <= 10; i++) {
            System.out.println("连队" + i + "开始领取武器");
            for (int j = 1; j <= 10; j++) {
                System.out.println("连队" + i + "的士兵" + j + "领取武器");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队" + i + "领取武器完毕");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new WrongWayStopThreadWithStopMethod());
        thread.start();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.stop();
    }
}
