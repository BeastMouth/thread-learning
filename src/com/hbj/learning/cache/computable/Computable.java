package com.hbj.learning.cache.computable;

/**
 * 有一个计算函数computer，用来代表耗时计算
 * 每个计算器都要实现这个接口，这样既可以无侵入实现缓存功能
 *
 * @author hbj
 * @date 2020/2/16 17:06
 */
public interface Computable<A, V> {
    /**
     * 计算接口
     *
     * @param arg 参数
     * @return 结果
     * @throws Exception 异常
     */
    V compute(A arg) throws Exception;
}
