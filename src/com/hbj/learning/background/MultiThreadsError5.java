package com.hbj.learning.background;

/**
 * 观察者模式
 *
 * @author hbj
 * @date 2019/11/5 17:28
 */
public class MultiThreadsError5 {
    int count;

    private MultiThreadsError5(MySource source) {
        source.registerListener(new EventListener() {
            @Override
            public void onEvent(Event e) {
                // 匿名内部类，持有对count等的引用，onEvent已经隐含的暴露了外部类的对象（隐式溢出，注册监听事件）
                System.out.println("\n我得到的数字是" + count);
            }

        });
        for (int i = 0; i < 10000; i++) {
            System.out.print(i);
        }
        count = 100;
    }

    private EventListener listener;

    // 利用工厂方法解决（先私有化构造器）
    public static MultiThreadsError5 getInstance(MySource source) {
        MultiThreadsError5 safeListener = new MultiThreadsError5(source);
        source.registerListener(safeListener.listener);
        return safeListener;
    }

    public static void main(String[] args) {
        MySource mySource = new MySource();
        MultiThreadsError5 multiThreadsError5 = MultiThreadsError5.getInstance(mySource);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mySource.eventCome(new Event() {
                });
            }
        }).start();
//        MultiThreadsError5 multiThreadsError5 = new MultiThreadsError5(mySource);
    }

    static class MySource {

        private EventListener listener;

        void registerListener(EventListener eventListener) {
            this.listener = eventListener;
        }

        void eventCome(Event e) {
            if (listener != null) {
                listener.onEvent(e);
            } else {
                System.out.println("还未初始化完毕");
            }
        }

    }

    interface EventListener {

        void onEvent(Event e);
    }

    interface Event {

    }
}
