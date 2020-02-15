package com.hbj.learning.future;

import java.util.concurrent.*;

/**
 * 演示get的超时方法。需要注意，超时后要处理，调用future.cancel
 * 演示cancel传入true，false的区别，代表的是：是否中断正在执行的任务
 *
 * @author hbj
 * @date 2020/2/15 23:38
 */
public class TimeOut {
    private static final Ad DEFAULT = new Ad("无网络时候的默认广告");
    private static final ExecutorService exec = Executors.newFixedThreadPool(10);

    static class Ad {
        String name;

        public Ad(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Ad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("sleep期间被中断了");
                return new Ad("被中断时候的默认广告");
            }
            return new Ad("旅游订票哪家强？找某程");
        }
    }

    private void printAd() {
        Future<Ad> f = exec.submit(new FetchAdTask());
        Ad ad;
        try {
            ad = f.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            ad = new Ad("被中断时候的默认广告");
            e.printStackTrace();
        } catch (ExecutionException e) {
            ad = new Ad("异常时候的默认广告");
            e.printStackTrace();
        } catch (TimeoutException e) {
            ad = new Ad("超时时候的默认广告");
            System.out.println("超时了，未获取到广告");
            // 取消时对任务返回的结果已经不关注了 true：给线程一个中断信号，false：线程不会被中断，继续运行
            boolean cancel = f.cancel(false);
            System.out.println("cancel的结果" + cancel);
        }
        exec.shutdown();
        System.out.println(ad.toString());
    }

    public static void main(String[] args) {
        TimeOut timeOut = new TimeOut();
        timeOut.printAd();
    }
}
