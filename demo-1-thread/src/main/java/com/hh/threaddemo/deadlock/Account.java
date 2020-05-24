package com.hh.threaddemo.deadlock;

import lombok.Data;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName Account.java
 * @Description 账户
 * @createTime 2020年05月24日
 */
@Data
public class Account {

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 余额
     */
    private int balance;

    public Account() {
    }

    public Account(String accountName, int balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    /**
     * 转出金额
     *
     * @param amount 转出的金额
     * @author: chenguoku
     * @date: 2020/5/24
     */
    public void debit(int amount) {
        this.balance -= amount;
    }

    /**
     * 转入金额
     *
     * @param amount 传入金额
     * @author: chenguoku
     * @date: 2020/5/24
     */
    public void credit(int amount) {
        this.balance += amount;
    }
}
