package com.hbj.learning.deadlock;

/**
 * @author hbj
 * @date 2020/1/11 16:27
 */
public class TransferMoney implements Runnable {
    int flag = 1;
    static Account a = new Account(500);
    static Account b = new Account(500);
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        TransferMoney r1 = new TransferMoney();
        TransferMoney r2 = new TransferMoney();
        r1.flag = 1;
        r2.flag = 0;
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a的余额" + a.balance);
        System.out.println("b的余额" + b.balance);
    }

    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        }
        if (flag == 0) {
            transferMoney(b, a, 200);
        }
    }

    public static void transferMoney(Account from, Account to, int amount) {
        // 修正方法（避免策略）
        class Helper {
            public void transfer() {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，转账失败");
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println("成功转账" + amount + "元");
            }
        }
        // 根据hashcode来决定获取锁顺序，冲突时（hash相同）需要“加时赛”（数据库里面可以用主键来作为获取锁的顺序的依据）
        // 当然，锁适用于业务场景不大的情况。假设数量很大，一般用事务保证转账的一致性和原子性，如果是跨行转账，可能还会用到消息队列进行异步处理，还有消息补偿的措施，例如分布式加锁等
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            // 冲突的时候，两个线程尝试获取新的一把锁
            synchronized (lock) {
                synchronized (to) {
                    synchronized (from) {
                        new Helper().transfer();
                    }
                }
            }
        }

        // 死锁
        synchronized (from) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (to) {
                if (from.balance - amount < 0) {
                    System.out.println("余额不足，转账失败");
                }
                from.balance -= amount;
                to.balance += amount;
                System.out.println("成功转账" + amount + "元");
            }
        }
    }

    static class Account {
        int balance;

        public Account(int balance) {
            this.balance = balance;
        }
    }
}
