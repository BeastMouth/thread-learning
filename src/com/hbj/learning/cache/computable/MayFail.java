package com.hbj.learning.cache.computable;

import java.io.IOException;

/**
 * 耗时计算的是实现类，有概率计算失败
 *
 * @author hbj
 * @date 2020/2/16 17:52
 */
public class MayFail implements Computable<String, Integer> {

    @Override
    public Integer compute(String arg) throws Exception {
        double random = Math.random();
        if (random > 0.5) {
            throw new IOException("读取文件出错");
        }
        Thread.sleep(3000);
        return Integer.valueOf(arg);
    }

}
