package com.hbj.learning.singleton;

/**
 * @author hbj
 * @date 2020/2/11 21:08
 */
public class DoubleCheckSingleton {
    /**
     * 为什么要加volatile？
     * 禁止jvm读实例化对象这个操作进行重排序操作
     * 实例化对象分为以下三步骤：
     * 1.新建一个对象
     * 2.调用对象的构造方法
     * 3.赋值
     * 若发生了重排序132，两个线程同时进入，A线程先抢到了锁，执行了13未执行2
     * B线程也进入，发现instance不为空，则直接使用，实际上instance里面的值还是为空的，便会出现空指针异常
     */
    private volatile static DoubleCheckSingleton instance;

    public DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                // 为什么要双重检查
                // 假如只检查一次，AB同时通过外面的if判断，都为空，都进来竞争锁
                // 假如没有内部判断，AB都会新建一个实例，这与单例相违背
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
