package com.hbj.learning.threadcoreknowledge.basicattributes;

/**
 * ID从1开始,我们创建的线程id早已不是2
 *
 * @author hbj
 * @date 2019/11/4 21:51
 */
public class ThreadId {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println("主线程的id " + Thread.currentThread().getId());
        System.out.println("子线程的id " + thread.getId());
    }
}
