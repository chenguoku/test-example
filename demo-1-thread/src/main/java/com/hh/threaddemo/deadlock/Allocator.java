package com.hh.threaddemo.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName Allocator.java
 * @Description 解决死锁，对象分配
 * @createTime 2020年05月24日
 */
public class Allocator {

    private List<Object> list = new ArrayList<Object>();

    /**
     * 一次申请到，转入账户和转出账户
     *
     * @param fromAccount 转出账户
     * @param toAccount   转入账户
     * @return: 是否申请到资源
     * @author: chenguoku
     * @date: 2020/5/24
     */
    synchronized boolean apply(Object fromAccount, Object toAccount) {
        if (list.contains(fromAccount) || list.contains(toAccount)) {
            return false;
        }
        list.add(fromAccount);
        list.add(toAccount);

        return true;
    }

    /**
     * 释放资源，账户处理完毕，释放对象
     *
     * @param fromAccount 转出账户
     * @param toAccount   转入账户
     * @author: chenguoku
     * @date: 2020/5/24
     */
    synchronized void free(Object fromAccount, Object toAccount) {
        list.remove(fromAccount);
        list.remove(toAccount);
    }
}
