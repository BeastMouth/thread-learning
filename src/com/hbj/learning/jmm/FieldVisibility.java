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

    // 等价（由于HappensBefore，b写入之前的所有操作都能够被后面的线程所感知）
//    int a = 1;
//    volatile int b = 2;

//    int a = 1;
//    int b = 2;

    private void change() {
        // 可能执行一句就刷到主内存
        a = 3;
        b = a;
    }

    //    private void print() {
//        System.out.println("a=" + a + ";b=" + b);
//    }
    private void print() {
        System.out.println("b=" + b + ";a=" + a);
    }

    // 没有加volatile关键字
    // a=3,b=3 第一个线程先执行，第二个线程再执行
    // a=1,b=2 第二个线程先执行，第一个线程在执行
    // a=3,b=2 两个线程交替运行
    // a=1,b=3(线程间通讯，线程1只把b刷回了主存，没有把a刷回主存)


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
