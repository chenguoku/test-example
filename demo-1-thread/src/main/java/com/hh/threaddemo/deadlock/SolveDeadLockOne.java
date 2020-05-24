package com.hh.threaddemo.deadlock;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName DeadLockDemo.java
 * @Description 解决死锁方案一：使用一个共享资源分配器，一次性将需要资源全部申请过来，破坏占有且等待，
 * @createTime 2020年05月24日
 */
public class SolveDeadLockOne implements Runnable {

    // 转出账户
    private Account fromAccount;
    // 转入账户
    private Account toAccount;
    // 金额
    private int amount;
    // 申请共享资源分配器
    private Allocator allocator;

    public SolveDeadLockOne() {
    }

    public SolveDeadLockOne(Account fromAccount, Account toAccount, int amount, Allocator allocator) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.allocator = allocator;
    }

    public void run() {
        while (true) {
            // 申请共享资源
            if (allocator.apply(fromAccount, toAccount)) {
                try {
                    synchronized (fromAccount) {
                        synchronized (toAccount) {
                            if (fromAccount.getBalance() > amount) {
                                fromAccount.debit(amount);
                                toAccount.credit(amount);
                            }
                        }
                    }

                    // 转出账户余额
                    System.out.println(fromAccount.getAccountName() + "->" + fromAccount.getBalance());

                    // 转入账户余额
                    System.out.println(toAccount.getAccountName() + "->" + toAccount.getBalance());
                } finally {
                    // 释放共享资源
                    allocator.free(fromAccount, toAccount);
                }

            }

        }
    }

    public static void main(String[] args) {
        Account a = new Account("A", 200000);
        Account b = new Account("B", 200000);
        Allocator allocator = new Allocator();

        Thread thread1 = new Thread(new SolveDeadLockOne(a, b, 2, allocator));
        Thread thread2 = new Thread(new SolveDeadLockOne(b, a, 3, allocator));

        thread1.start();
        thread2.start();
    }
}
