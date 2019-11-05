package com.hbj.learning.background;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布溢出
 * 一个对象是指使对象能够在当前作用域之外的代码中使用。
 * 可以通过 公有静态变量，非私有方法，构造方法内隐含引用 三种方式。
 * 如果对象构造完成之前就发布该对象，就会破坏线程安全性。
 * 当某个不应该发布的对象被发布时，这种情况就被称为逸出（Escape）
 *
 * @author hbj
 * @date 2019/11/5 17:07
 */
public class MultiThreadsError3 {
    private Map<String, String> states;

    public MultiThreadsError3() {
        states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
    }

    public Map<String, String> getStates() {
        // private对象内容会被篡改
        return states;
    }

    public Map<String, String> getStatesImproved() {
        // private对象内容不会被篡改
        // 用副本代替真身
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        MultiThreadsError3 multiThreadsError3 = new MultiThreadsError3();
//        Map<String, String> states = multiThreadsError3.getStates();
//        System.out.println(states.get("1"));
//        states.remove("1");
//        System.out.println(states.get("1"));

        System.out.println(multiThreadsError3.getStatesImproved().get("1"));
        multiThreadsError3.getStatesImproved().remove("1");
        System.out.println(multiThreadsError3.getStatesImproved().get("1"));

    }
}
