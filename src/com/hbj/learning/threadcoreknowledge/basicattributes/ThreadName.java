package com.hbj.learning.threadcoreknowledge.basicattributes;

/**
 * @author hbj
 * @date 2019/11/4 22:23
 */
public class ThreadName {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        System.out.println("子线程的名称:" + thread.getName());
        thread.setName("子线程改名");
        System.out.println("子线程的名称:" + thread.getName());
    }
}
