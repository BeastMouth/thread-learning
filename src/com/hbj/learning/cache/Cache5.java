package com.hbj.learning.cache;

import com.hbj.learning.cache.computable.Computable;
import com.hbj.learning.cache.computable.ExpensiveFunction;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 用装饰着模式给计算器自动添加缓存功能
 * 减小锁的粒度(还是有现成安全问题)
 * 使用ConcurrentHashMap来替代HashMap
 * 缺点：在计算完成前，如果有两个请求进来计算相同的内容，就会有计算多遍的问题
 *
 * @author hbj
 * @date 2020/2/16 17:10
 */
public class Cache5<A, V> implements Computable<A, V> {

    private final ConcurrentHashMap<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Cache5(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Cache5<String, Integer> expensiveComputer = new Cache5<>(new ExpensiveFunction());
        Thread thread1 = new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第一次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("777");
                System.out.println("第二次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                Integer result = expensiveComputer.compute("666");
                System.out.println("第三次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

    }
}
