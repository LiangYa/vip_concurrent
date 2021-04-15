package com.xiangxue.ch1;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: join方法的测试
 * @create : 2021/1/12
 */
public class UserJoin {

    /**
     * 插队类
     */
    static class JumpQueue implements Runnable {
        /**
         * 这个是插队的线程
         */
        private Thread thread;
        public JumpQueue(Thread thread){
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" terminated.");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JumpQueue(previous),String.valueOf(i));
            System.out.println(previous.getName()+" Jump a queue the thread: "+ thread.getName());
            thread.start();
            previous = thread;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" terminated.");
    }
}
