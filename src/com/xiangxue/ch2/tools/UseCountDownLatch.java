package com.xiangxue.ch2.tools;

import java.util.concurrent.CountDownLatch;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: 有5个初始化的线程，6个扣除点，
 * 扣除完毕以后，主线程和业务线程才能继续自己的工作
 * @create : 2021/3/25
 */
public class UseCountDownLatch {
    public static CountDownLatch latch = new CountDownLatch(6);
    private static class InitThread implements Runnable{
        @Override
        public void run() {
            System.out.println("Thread_"+Thread.currentThread().getId()
                    +" ready init work.......");
            //初始化线程完成工作了，
            latch.countDown();
            for (int i = 0; i < 2; i++) {
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + "......continue do its work");
            }
        }
    }
    private static class BusiThread implements Runnable {
        @Override
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println("BusiThread_"+Thread.currentThread().getId()
                        + " do business......");
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //单独的初始化线程，初始化分为两步，需要扣减两次
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("Thread_"+Thread.currentThread().getId()
                            +" ready init work step 1st.....");
                    //每完成一步初始化工作，扣减一次
                    latch.countDown();
                    System.out.println("begin step 2nd.....");
                    Thread.sleep(1000);
                    System.out.println("Thread_"+Thread.currentThread().getId()
                            +" ready init work step 2nd......");
                    //每完成一步初始化工作，扣减一次
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new BusiThread()).start();
        for (int i = 0; i <= 3; i++){
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();
        System.out.println("Main do its work ......");
    }
}
