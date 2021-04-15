package com.xiangxue.ch3;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: 时间戳
 * @create : 2021/4/11
 */
public class UseAtomicStampedReference {
    static AtomicStampedReference<String> stampedReference =
            new AtomicStampedReference("Liangya",0);

    public static void main(String[] args) throws InterruptedException {
        final int oldStamp = stampedReference.getStamp();
        final String oldRe = stampedReference.getReference();
        System.out.println("----------------");
        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                Boolean isTrue = stampedReference.compareAndSet(oldRe,"liangya2", oldStamp,oldStamp+1);
                System.out.println("当前变量值："+oldRe+"时间搓"+oldStamp+"--"+isTrue);

            }
        });

        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                String re = stampedReference.getReference();
                int stamp = stampedReference.getStamp();
                Boolean isTrue = stampedReference.compareAndSet(re,"liangya3", oldStamp,stamp+1);
                System.out.println("当前变量值："+re+"时间搓"+stamp+"--"+isTrue);

            }
        });

        one.start();
        one.join();
        two.start();
        two.join();
    }
}
