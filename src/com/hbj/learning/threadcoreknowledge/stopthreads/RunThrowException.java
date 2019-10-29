package com.hbj.learning.threadcoreknowledge.stopthreads;

/**
 * run方法无法在向外抛出checked Exception，只能用try/catch
 *
 * @author hbj
 * @date 2019/10/29 22:15
 */
public class RunThrowException {
    public void aVoid() throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() throws Exception {
//                throw new Exception();
//            }
//        });
    }
}
