package com.hbj.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 两个线程打印日期
 *
 * @author hbj
 * @date 2020/1/16 14:11
 */
public class ThreadLocalNormalUsage00 {
    public String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT(应该，标准时区)计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(10);
                System.out.println(date);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String date = new ThreadLocalNormalUsage00().date(1007);
                System.out.println(date);
            }
        }).start();
    }
}
