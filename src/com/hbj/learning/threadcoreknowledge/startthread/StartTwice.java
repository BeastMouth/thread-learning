package com.hbj.learning.threadcoreknowledge.startthread;

/**
 * start两次会有什么问题（不能重复调用start方法，否则会报错）
 *
 * @author hbj
 * @date 2019/10/24 22:32
 */
public class StartTwice {
    public static void main(String[] args) {
        Thread thread = new Thread();
        // 这一步由主线程执行
        thread.start();
        thread.start();
    }
}

