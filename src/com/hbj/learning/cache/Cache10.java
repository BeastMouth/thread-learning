package com.hbj.learning.cache;

import com.hbj.learning.cache.computable.Computable;
import com.hbj.learning.cache.computable.MayFail;

import java.util.concurrent.*;

/**
 * 用装饰着模式给计算器自动添加缓存功能
 * 减小锁的粒度(还是有现成安全问题)
 * 使用ConcurrentHashMap来替代HashMap
 * 缺点：在计算完成前，如果有两个请求进来计算相同的内容，就会有计算多遍的问题
 * 利用Future避免重复计算
 * 用原子操作 putIfAbsent 操作解决
 * 处于安全性考虑，缓存需要设置有效期，到期自动失效，否则如果缓存一直不失效，会有缓存不一致的问题
 *
 * @author hbj
 * @date 2020/2/16 17:10
 */
public class Cache10<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Cache10(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        while (true) {
            // 还有问题：两个线程同时调用get(666)
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<>(callable);
                // 反正重复计算
                // putIfAbsent 返回的是放置ft之前arg对应的value
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    System.out.println("从FutureTask调用了计算函数");
                    ft.run();
                }
            }
            // 有结果返回，没有结果就阻塞
            try {
                return f.get();
            } catch (CancellationException e) {
                // 人为取消，应该立刻终止
                System.out.println("被取消了");
                cache.remove(arg);
                throw e;
            } catch (InterruptedException e) {
                // 人为取消，应该立刻终止
                cache.remove(arg);
                throw e;
            } catch (ExecutionException e) {
                // 计算出错，重试，直到计算成功
                // 无限重试，缓存污染，需要移除Future
                System.out.println("计算错误，需要重试");
                cache.remove(arg);
            }
        }
    }

    public final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

    public V compute(A arg, Long expire) throws Exception {
        if (expire > 0) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    expire(arg);
                }
            }, expire, TimeUnit.MILLISECONDS);
        }
        return compute(arg);
    }

    public V computeRandomExpire(A arg) throws Exception {
        long randomExpire = (long) (Math.random() * 10000);
        return compute(arg, randomExpire);
    }

    public synchronized void expire(A key) {
        Future<V> future = cache.get(key);
        if (future != null) {
            if (future.isDone()) {
                System.out.println("Future任务被取消");
                future.cancel(true);
            }
            System.out.println("过期时间到，缓存被清除");
            cache.remove(key);
        }
    }

    public static void main(String[] args) throws Exception {
        Cache10<String, Integer> expensiveComputer = new Cache10<>(new MayFail());
        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666", 5000L);
                System.out.println("第一次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("777");
                System.out.println("第二次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第三次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(6000);
        Integer result = expensiveComputer.compute("666");
        System.out.println(result);
    }
}
