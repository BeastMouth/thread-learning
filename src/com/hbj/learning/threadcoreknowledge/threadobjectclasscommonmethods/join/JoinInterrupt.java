package com.hbj.learning.threadcoreknowledge.threadobjectclasscommonmethods.join;

/**
 * @author hbj
 * @date 2019/11/4 20:56
 */
public class JoinInterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 此处中断主线程
                mainThread.interrupt();
                try {
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + "finished");
                } catch (InterruptedException e) {
                    // 接收到主线程推送过来的中断
//                    e.printStackTrace();
                    System.out.println("子线程接收到了来自主线程推送的中断");
                }
            }
        });
        thread1.start();
        System.out.println("等待子线程运行完毕");
        try {
            thread1.join();
        } catch (InterruptedException e) {
            // 此处中断只是中断了主线程，子线程没有被中断
            System.out.println(Thread.currentThread().getName() + "中断了");
//            e.printStackTrace();
            // 将中断推送给子线程
            thread1.interrupt();
        }
        // 中断后的执行与子线程是否执行完没有关系
        System.out.println("子线程执行完毕");
    }
}
