package com.xiangxue.ch3.answer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/4/11
 */
public class HalAtomicInt {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public void increament() {
        for (;;){
            int i = atomicInteger.get();
            boolean suc = atomicInteger.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    public static void main(String[] args) {

    }
}
