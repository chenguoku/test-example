package com.hh.threaddemo.threadlocal;

import org.junit.Test;

/**
 * @author chenguoku
 * @version 1.0.0
 * @ClassName ThreadLocalDemo.java
 * @Description ThreadLocal练习
 * @createTime 2020年05月24日
 */
public class ThreadLocalDemo {

    private static ThreadLocal<Object> localOne = new ThreadLocal<Object>();

    private static ThreadLocal<Object> localTwo = new ThreadLocal<Object>() {
        @Override
        protected Object initialValue() {
            // 设置变量默认值
            return 1;
        }
    };

    /**
     * 练习set和get方法
     *
     * @author: chenguoku
     * @date: 2020/5/24
     */
    @Test
    public void testSetAndGet() {
        Object one = localOne.get();
        Object two = localTwo.get();

        System.out.println(String.valueOf(one));
        System.out.println(String.valueOf(two));

        Thread a = new Thread(() -> {
            localOne.set("a");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = localOne.get();
            System.out.println("A 线程的 localOne的值" + String.valueOf(o));
        });

        Thread b = new Thread(() -> {
            localOne.set("b");
            Object o = localOne.get();
            System.out.println("B 线程的 localOne的值" + String.valueOf(o));
        });
        a.start();
        b.start();

        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
