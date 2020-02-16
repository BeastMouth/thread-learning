package com.hbj.learning.cache;

import com.hbj.learning.cache.computable.Computable;
import com.hbj.learning.cache.computable.ExpensiveFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * 用装饰着模式给计算器自动添加缓存功能
 *
 * @author hbj
 * @date 2020/2/16 17:10
 */
public class Cache3<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new HashMap();
    private final Computable<A, V> c;

    public Cache3(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Cache3<String, Integer> expensiveComputer = new Cache3<>(new ExpensiveFunction());
        Thread thread1 = new Thread(() -> {
            Integer result = null;
            try {
                result = expensiveComputer.compute("666");
                System.out.println("第一次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            Integer result = null;
            try {
                result = expensiveComputer.compute("777");
                System.out.println("第二次计算结果:" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread3 = new Thread(() -> {
            Integer result = null;
            try {
                result = expensiveComputer.compute("666");
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
