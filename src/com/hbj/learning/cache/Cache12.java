package com.hbj.learning.cache;

import com.hbj.learning.cache.computable.ExpensiveFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hbj
 * @date 2020/2/16 18:58
 */
public class Cache12 {
    static Cache10<String, Integer> expensiveComputer = new Cache10<>(new ExpensiveFunction());

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(10000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            service.submit(() -> {
                Integer result = null;
                try {
                    System.out.println(Thread.currentThread().getName() + "开始等待");
                    countDownLatch.await();
                    SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
                    String time = dateFormat.format(new Date());
                    System.out.println(Thread.currentThread().getName() + "    " + time + "被放行");
                    result = expensiveComputer.compute("666");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(result);
            });
        }
        Thread.sleep(5000);
        countDownLatch.countDown();
        service.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));
    }
}

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("mm:ss");
        }

        @Override
        public SimpleDateFormat get() {
            return super.get();
        }
    };

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal2 = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));


}