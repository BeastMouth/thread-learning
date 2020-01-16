package com.hbj.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 30个线程打印日期
 *
 * @author hbj
 * @date 2020/1/16 14:11
 */
public class ThreadLocalNormalUsage01 {
    public String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT(应该，标准时区)计时
        Date date = new Date(1000 * seconds);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage01().date(finalI);
                    System.out.println(date);
                }
            }).start();
            Thread.sleep(100);
        }
    }
}
