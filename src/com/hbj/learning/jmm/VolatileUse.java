package com.hbj.learning.jmm;

/**
 * 作为触发器
 *
 * @author hbj
 * @date 2020/1/15 22:01
 */
public class VolatileUse {
    private static boolean flag = false;
    private static int i = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("增加前的i" + i);
                i++;
                System.out.println("i已经加完啦！");
                flag = true;
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flag) {
                    System.out.println("等待另一个线程对i进行处理");
                }
                System.out.println("被另一个线程加完的i为" + i);
            }
        });
        thread2.start();
        thread1.start();
    }
}
