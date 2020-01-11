package com.hbj.learning.deadlock;

import java.util.Random;

/**
 * @author hbj
 * @date 2020/1/11 16:27
 */
public class MultiTransferMoney {
    private static final int NUM_ACCOUNTS = 50;
    private static final int NUM_MONEY = 1000;
    private static final int NUM_ITERATIONS = 1000000;
    private static final int NUM_THREADS = 20;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        TransferMoney.Account[] accounts = new TransferMoney.Account[NUM_ACCOUNTS];
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            accounts[i] = new TransferMoney.Account(NUM_MONEY);
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcct = random.nextInt(NUM_ACCOUNTS);
                    int toAcct = random.nextInt(NUM_ACCOUNTS);
                    int amount = random.nextInt(NUM_MONEY);
                    TransferMoney.transferMoney(accounts[fromAcct], accounts[toAcct], amount);
                }
            }
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }
}
