package com.xiangxue.ch3;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/4/11
 */
public class UseAtomicInt {
    static AtomicInteger ai = new AtomicInteger(10);

    public static void main(String[] args) {
        System.out.println(ai.getAndIncrement());
        System.out.println(ai.incrementAndGet());
        System.out.println(ai.get());
    }
}
