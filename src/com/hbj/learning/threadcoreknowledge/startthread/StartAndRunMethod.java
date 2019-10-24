package com.hbj.learning.threadcoreknowledge.startthread;

/**
 * 线程启动的两种方法
 * run 和 start
 * start一个线程的三步操作：
 * 1.校验线程状态
 * 2.加入线程组
 * 3.调用原生的start0方法
 * <p>
 * 运行线程由线程调度器来决定（调用start方法告诉jvm在合适的时间来启动线程
 * 线程状态的变化：先处于就绪状态（获取到除了cpu以外的其他资源 上下文，栈等）
 * 就绪后才会被jvm或者操作系统调度到执行状态 等待获取cpu
 * 然后才进入运行状态）
 *
 * @author hbj
 * @date 2019/10/24 22:04
 */
public class StartAndRunMethod {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        runnable.run();
        new Thread(runnable).start();
    }
}
