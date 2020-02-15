package com.hbj.learning.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 演示get方法过程中抛出异常，for循环为了演示抛出异常的时机，并不是一产生异常就抛出，直到我们get时才抛出
 *
 * @author hbj
 * @date 2020/2/15 23:38
 */
public class GetException {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(20);
        Future<Object> future = service.submit(() -> {
            throw new IllegalAccessException("Callable 抛出异常");
        });
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                Thread.sleep(500);
            }
            System.out.println(future.isDone());
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("抛出了InterruptedException异常");
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("抛出了ExecutionException异常");
        }
        service.shutdown();
    }
}
