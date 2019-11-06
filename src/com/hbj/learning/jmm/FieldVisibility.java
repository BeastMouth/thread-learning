package com.hbj.learning.jmm;

/**
 * 演示可见性带来的问题
 * b=3;a=1(第二个线程可能看不见或者看到一部分第一个线程的操作)
 *
 * @author hbj
 * @date 2019/11/6 23:08
 */
public class FieldVisibility {

    volatile int a = 1;
    volatile int b = 2;

    private void change() {
        // 可能执行一句就刷到主内存
        a = 3;
        b = a;
    }

    private void print() {
        System.out.println("b=" + b + ";a=" + a);
    }

    public static void main(String[] args) {
        while (true) {
            FieldVisibility test = new FieldVisibility();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();
        }
    }
}
