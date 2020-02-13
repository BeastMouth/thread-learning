package com.hbj.learning.flowcontrol.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步
 * 当所有人到终点后，比赛结束
 * 运动员等待发令
 * 多等一（适用于服务器压测）
 *
 * @author hbj
 * @date 2020/2/13 21:10
 */
public class CountDownLatchDemo2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("No." + no + "准备完毕，等待发令枪");
                    try {
                        begin.await();
                        System.out.println("No." + no + "开始跑步");
                        Thread.sleep((long) (Math.random() * 1000));
                        System.out.println("No." + no + "跑到终点");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        end.countDown();
                    }
                }
            };
            service.submit(runnable);
        }
        System.out.println("裁判员检查发令枪场地等...");
        Thread.sleep(5000);
        System.out.println("发令枪响，比赛开始");
        begin.countDown();
        end.await();
        System.out.println("所有人到达终点，比赛结束");
        service.shutdown();
    }
}
