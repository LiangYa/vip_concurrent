package com.xiangxue.ch1;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: sleep和yield是不会释放锁的
 * @create : 2021/1/12
 */
public class SleepLock {
    private Object lock = new Object();

    public static void main(String[] args) {
        SleepLock sleepTest = new SleepLock();
        Thread threadA = sleepTest.new ThreadSleep();
        threadA.setName("ThreadSleep");
        Thread threadB = sleepTest.new ThreadNotSleep();
        threadB.setName("threadNotSleep");
        threadA.start();
        try {
            Thread.sleep(1000);
            System.out.println(" Main slept");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadB.start();
    }

    private class ThreadSleep extends Thread{
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock");
            try {
                synchronized (lock){
                    System.out.println(threadName+" taking the lock");
                    Thread.sleep(5000);
                    System.out.println(" Finish the work: "+ threadName);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private class ThreadNotSleep extends Thread{
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " will take the lock");
            try {
                synchronized (lock){
                    System.out.println(threadName+" taking the lock");
                    System.out.println(" Finish the work: "+ threadName);
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
