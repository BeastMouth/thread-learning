package com.hbj.learning.livelock;

import java.util.Random;

/**
 * 活锁：结果与死锁一样，而且一直在消耗cpu资源，还不如死锁
 *
 * @author hbj
 * @date 2020/1/11 20:02
 */
public class LiveLock {
    public static void main(String[] args) {
        Diner husband = new Diner("牛郎");
        Diner wife = new Diner("织女");
        Spoon spoon = new Spoon(husband);
        new Thread(new Runnable() {
            @Override
            public void run() {
                husband.eatWith(spoon, wife);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                wife.eatWith(spoon, husband);
            }
        }).start();
    }

    static class Spoon {
        private Diner owner;

        public Spoon(Diner owner) {
            this.owner = owner;
        }

        public Diner getOwner() {
            return owner;
        }

        public void setOwner(Diner owner) {
            this.owner = owner;
        }

        public synchronized void use() {
            System.out.printf("%s 已经开始吃饭啦！", owner.name);
        }
    }

    static class Diner {
        private String name;
        private boolean isHungry = true;

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                if (spoon.owner != this) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                Random random = new Random();
                if (spouse.isHungry && random.nextInt(10) < 9) {
                    System.out.println(name + "：亲爱的" + spouse.name + "你先吃把");
                    spoon.setOwner(spouse);
                    continue;
                }
                spoon.use();
                isHungry = false;
                System.out.println(name + "：我吃完了");
                spoon.setOwner(spouse);
            }
        }

        public Diner(String name) {
            this.name = name;
        }
    }
}
