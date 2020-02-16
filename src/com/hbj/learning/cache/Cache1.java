package com.hbj.learning.cache;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * 有问题，HashMap并发不安全
 *
 * @author hbj
 * @date 2020/2/16 16:46
 */
public class Cache1 {
    /**
     * 属性加上final关键字，该变量只能被赋值一次。
     * 一旦被赋值，final的变量就不能再被改变
     * 增加了安全性
     */
    private final HashMap<String, Integer> cache = new HashMap<>();

//    public Integer compute(String userId) throws InterruptedException {
//        Integer result = cache.get(userId);
//        // 先检查HashMap里面有没有保存过之前计算的结果
//        if (result == null) {
//            // 如果缓存中找不到，那么需要现在来计算一下结果，并且保存到HashMap中
//            result = doCompute(userId);
//            cache.put(userId, result);
//        }
//        return result;
//    }

    /**
     * 升级，性能差（每次只能一个线程进来）-与缓存一开始设计思路不同
     * 代码复用性差
     */
    public synchronized Integer compute(String userId) throws InterruptedException {
        Integer result = cache.get(userId);
        // 先检查HashMap里面有没有保存过之前计算的结果
        if (result == null) {
            // 如果缓存中找不到，那么需要现在来计算一下结果，并且保存到HashMap中
            result = doCompute(userId);
            cache.put(userId, result);
        }
        return result;
    }

    private Integer doCompute(String userId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return new Integer(userId);
    }

    public static void main(String[] args) throws InterruptedException {
        Cache1 cache1 = new Cache1();
        System.out.println("开始计算啦");
        Integer result = cache1.compute("13");
        System.out.println("第一次计算结果:" + result);
        result = cache1.compute("13");
        System.out.println("第二次计算结果:" + result);
    }
}
