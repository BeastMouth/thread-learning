package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods.test.twothreadprint100;

/**
 * 两个线程交替打印0~100奇偶数
 *
 * @author hbj
 * @date 2019/10/31 21:29
 */
public class TwoThreadPrintOneHundred {

    public static void main(String[] args) {
        NumberChange numberChange = new NumberChange(0);
        Thread threadOddPrint = new Thread(new OddRunnable(numberChange));
        Thread threadEvenPrint = new Thread(new EvenRunnable(numberChange));
        threadOddPrint.start();
        threadEvenPrint.start();
    }
}

class OddRunnable implements Runnable {

    private NumberChange numberChange;

    public OddRunnable(NumberChange numberChange) {
        this.numberChange = numberChange;
    }

    @Override
    public void run() {
        numberChange.printNumberOdd();
    }
}

class EvenRunnable implements Runnable {

    private NumberChange numberChange;

    public EvenRunnable(NumberChange numberChange) {
        this.numberChange = numberChange;
    }

    @Override
    public void run() {
        numberChange.printNumberEven();
    }
}

class NumberChange {
    private Integer num;

    public NumberChange(Integer num) {
        this.num = num;
    }

    public synchronized void printNumberOdd() {
        while (num < 100) {
            // 为偶数，等待唤醒
            if (num % 2 == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 为奇数，打印自增及唤醒打印偶数的线程
            System.out.println("thread name : " + Thread.currentThread().getName() + " num is " + num);
            num++;
            notify();
        }
    }

    public synchronized void printNumberEven() {
        while (num < 100) {
            // 为奇数，等待唤醒
            if (num % 2 == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 为偶数，打印自增并唤醒打印奇数的线程
            System.out.println("thread name : " + Thread.currentThread().getName() + " num is " + num);
            num++;
            notify();
        }
    }
}
