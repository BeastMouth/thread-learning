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
 * 利用ThreadLocal给每个线程分配自己的dateFormat对象，保证了线程安全，高效利用内存
 *
 * @author hbj
 * @date 2020/1/16 14:11
 */
public class ThreadLocalNormalUsage05 {
    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public String date(int seconds) {
        // 参数的单位是毫秒，从1970.1.1 00:00:00 GMT(应该，标准时区)计时
        Date date = new Date(1000 * seconds);
        // 新建了1000个dateFormat对象
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal2.get();
        return dateFormat.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        // 实际上set不是线程安全的
//        Set<String> set = new HashSet<>();
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String date = new ThreadLocalNormalUsage05().date(finalI);
                    set.add(date);
                    System.out.println(date);
                }
            });
        }
        Thread.sleep(1000);
        System.out.println(set.size());
        threadPool.shutdown();
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }
    };
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

}