package com.hbj.learning.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1000个打印日期的任务，利用线程池来执行
 * dateFormat是静态的变量
 * 加锁解决线程安全问题
 * 锁效率较低，不能并行
 *
 * @author hbj
 * @date 2020/1/16 14:11
 */
public class ThreadLocalNormalUsage04 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    /**
     * 当所有线程共用一个dateFormat，便会发生线程安全问题
     */
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT(应该，标准时区)计时
        Date date = new Date(1000 * seconds);
        // 新建了1000个dateFormat对象
        String format;
        synchronized (ThreadLocalNormalUsage04.class) {
            format = dateFormat.format(date);
        }
        return format;

    }

    public static void main(String[] args) throws InterruptedException {
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage04().date(finalI);
                    set.add(date);
                    System.out.println(date);
                }
            });
        }
        threadPool.shutdown();
        Thread.sleep(1000);
        System.out.println(set.size());
    }
}
