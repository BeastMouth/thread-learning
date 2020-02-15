package com.hbj.learning.future;

import java.util.concurrent.*;

/**
 * 演示FutureTask的用法
 *
 * @author hbj
 * @date 2020/2/16 0:19
 */
public class FutureTaskDemo {
    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> integerFutureTask = new FutureTask<>(task);
//        new Thread(integerFutureTask).start();
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.submit(integerFutureTask);
        try {
            Integer integer = integerFutureTask.get();
            System.out.println("Task运行结果：" + integer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}

class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("子线程正在计算");
        Thread.sleep(3000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}
