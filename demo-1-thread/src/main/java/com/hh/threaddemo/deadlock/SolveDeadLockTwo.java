package com.hh.threaddemo.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName DeadLockDemo.java
 * @Description 解决死锁方案二：使用同一个ReentrantLock每次尝试去加锁，加锁失败就循环下一次加锁，破坏不可抢占
 * @createTime 2020年05月24日
 */
public class SolveDeadLockTwo implements Runnable {

    // 转出账户
    private Account fromAccount;
    // 转入账户
    private Account toAccount;
    // 金额
    private int amount;

    private Lock fromLock;
    private Lock toLock;

    public SolveDeadLockTwo() {
    }

    public SolveDeadLockTwo(Account fromAccount, Account toAccount, int amount, Lock fromLock, Lock toLock) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.fromLock = fromLock;
        this.toLock = toLock;
    }

    public void run() {
        while (true) {
            if (fromLock.tryLock()) {
                if (toLock.tryLock()) {
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
        }
    }

    public static void main(String[] args) {
        Account a = new Account("A", 200000);
        Account b = new Account("B", 200000);

        ReentrantLock fromLock = new ReentrantLock();
        ReentrantLock toLock = new ReentrantLock();

        Thread thread1 = new Thread(new SolveDeadLockTwo(a, b, 2, fromLock, toLock));
        Thread thread2 = new Thread(new SolveDeadLockTwo(b, a, 3, fromLock, toLock));

        thread1.start();
        thread2.start();
    }
}
