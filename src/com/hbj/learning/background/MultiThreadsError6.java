package com.hbj.learning.background;

import java.util.HashMap;
import java.util.Map;

/**
 * 构造函数中运行线程
 *
 * @author hbj
 * @date 2019/11/5 17:07
 */
public class MultiThreadsError6 {
    private Map<String, String> states;

    public MultiThreadsError6() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                states = new HashMap<>();
                states.put("1", "周一");
                states.put("2", "周二");
                states.put("3", "周三");
                states.put("4", "周四");
            }
        }).start();
    }

    public Map<String, String> getStates() {
        return states;
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadsError6 multiThreadsError3 = new MultiThreadsError6();
        Thread.sleep(1000);
        Map<String, String> states = multiThreadsError3.getStates();
        System.out.println(states.get("1"));
    }
}
