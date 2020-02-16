package com.hbj.learning.cache;

import com.hbj.learning.cache.computable.Computable;
import com.hbj.learning.cache.computable.ExpensiveFunction;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 用装饰着模式给计算器自动添加缓存功能
 * 减小锁的粒度(还是有现成安全问题)
 * 使用ConcurrentHashMap来替代HashMap
 * 缺点：在计算完成前，如果有两个请求进来计算相同的内容，就会有计算多遍的问题
 * 利用Future避免重复计算
 *
 * @author hbj
 * @date 2020/2/16 17:10
 */
public class Cache7<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Cache7(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
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
            f = ft;
            // 反正重复计算
            cache.put(arg, ft);
            System.out.println("从FutureTask调用了计算函数");
            ft.run();
        }
        // 有结果返回，没有结果就阻塞
        return f.get();
    }

    public static void main(String[] args) throws Exception {
        Cache7<String, Integer> expensiveComputer = new Cache7<>(new ExpensiveFunction());
        new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
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
    }
}
