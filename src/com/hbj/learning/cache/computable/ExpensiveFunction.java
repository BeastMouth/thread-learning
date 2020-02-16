package com.hbj.learning.cache.computable;

/**
 * 耗时计算的实现类，实现Computable接口
 * 本身不具备缓存能力，不需要考虑缓存的事情
 *
 * @author hbj
 * @date 2020/2/16 17:08
 */
public class ExpensiveFunction implements Computable<String, Integer> {
    @Override
    public Integer compute(String arg) throws Exception {
        Thread.sleep(5000);
        return Integer.valueOf(arg);
    }
}
