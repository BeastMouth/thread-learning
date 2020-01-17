package com.hbj.learning.threadlocal;

import java.text.SimpleDateFormat;

/**
 * 演示ThreadLocal用法2：避免传递参数的麻烦
 *
 * @author hbj
 * @date 2020/1/16 14:57
 */
public class ThreadLocalNormalUsage10 {
    public static String str;

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入线程" + Thread.currentThread().getName());
                str = "小黄";
                try {
                    new Service1().process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("进入线程" + Thread.currentThread().getName());
                str = "小阮";
                try {
                    new Service1().process();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

class Service1 {
    public void process() throws InterruptedException {
        User user = new User(ThreadLocalNormalUsage10.str);
        UserContextHolder.holder.set(user);
        Thread.sleep(1000);
        new Service2().process();
    }
}

class Service2 {
    public void process() {
        // 这里holder，dateFormatThreadLocal都是ThreadLocal，他们作为key，存在了ThreadLocalMap里面，value是设置进去的对象，例如holder里面的user对象
        User user = UserContextHolder.holder.get();
        SimpleDateFormat simpleDateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
        System.out.println(Thread.currentThread().getName() + "Service2拿到用户名 : " + user.getName());
        new Service3().process();
    }
}

class Service3 {
    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println(Thread.currentThread().getName() + "Service3拿到用户名 : " + user.getName());
        UserContextHolder.holder.remove();
        ThreadSafeFormatter.dateFormatThreadLocal.remove();
    }
}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }
}