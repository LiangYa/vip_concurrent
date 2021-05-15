package com.xiangxue.ch7;

import com.xiangxue.tools.SleepTools;

public class NormalDeadLock {
    /**
     * 第一个锁
     */
    private static Object valueFirst = new Object();
    /**
     * 第二个锁
     */
    private static Object valueSecond = new Object();

    /**
     * 先那第一个锁，再拿第二个锁
     */
    private static void firstToSecond() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst) {
            System.out.println(threadName+" get first");
            SleepTools.ms(100);
            synchronized (valueSecond) {
                System.out.println(threadName+" get second");
            }
        }
    }

    /**
     * 先拿第二个锁，再拿第一个锁
     */
    private static void secondToFirst() throws InterruptedException{
        String threadName = Thread.currentThread().getName();
        synchronized (valueFirst) {
            System.out.println(threadName+" get first");
            SleepTools.ms(100);
            synchronized (valueSecond){
                System.out.println(threadName+" get second");
            }
        }
    }

    private static class TestThread extends Thread{
        private String name;
        public TestThread(String name){
            this.name = name;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            TestThread testThread = new TestThread("SubTestThread");
            testThread.start();
            try {
                firstToSecond();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("TestDeadLock");
        TestThread testThread = new TestThread("SubTestThread");
        testThread.start();
        try {
            //先拿第一个锁，再拿第二个锁
            firstToSecond();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
