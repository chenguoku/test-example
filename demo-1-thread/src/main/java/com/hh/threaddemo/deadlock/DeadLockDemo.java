package com.hh.threaddemo.deadlock;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName DeadLockDemo.java
 * @Description 死锁demo
 * @createTime 2020年05月24日
 */
public class DeadLockDemo implements Runnable {

    // 转出账户
    private Account fromAccount;
    // 转入账户
    private Account toAccount;
    // 金额
    private int amount;

    public DeadLockDemo() {
    }

    public DeadLockDemo(Account fromAccount, Account toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void run() {
        while (true) {
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
        }
    }

    public static void main(String[] args) {
        Account a = new Account("A", 200000);
        Account b = new Account("B", 200000);

        Thread thread1 = new Thread(new DeadLockDemo(a, b, 2));
        Thread thread2 = new Thread(new DeadLockDemo(b, a, 3));

        thread1.start();
        thread2.start();
    }
}
