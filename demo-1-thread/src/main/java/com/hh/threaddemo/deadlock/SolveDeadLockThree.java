package com.hh.threaddemo.deadlock;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName DeadLockDemo.java
 * @Description 解决死锁方案三：相同的顺序操作共享资源，防止互相等待，破坏循环等待
 * @createTime 2020年05月24日
 */
public class SolveDeadLockThree implements Runnable {

    // 转出账户
    private Account fromAccount;
    // 转入账户
    private Account toAccount;
    // 金额
    private int amount;

    public SolveDeadLockThree() {
    }

    public SolveDeadLockThree(Account fromAccount, Account toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public void run() {
        Account left = fromAccount;
        Account right = toAccount;
        if (fromAccount.hashCode() > toAccount.hashCode()) {
            left = toAccount;
            right = fromAccount;
        }

        while (true) {
            synchronized (left) {
                synchronized (right) {
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

        Thread thread1 = new Thread(new SolveDeadLockThree(a, b, 2));
        Thread thread2 = new Thread(new SolveDeadLockThree(b, a, 3));

        thread1.start();
        thread2.start();
    }
}
