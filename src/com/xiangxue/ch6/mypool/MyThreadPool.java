package com.xiangxue.ch6.mypool;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @auther liangya
 * @date 2021/5/5 16:30
 */
public class MyThreadPool {
    /**
     * 线程池默认的线程个数
     */
    private static int WORK_NUM = 5;
    /**
     * 队列默认任务个数为100
     */
    private static int TASK_COUNT = 100;

    /**
     * 工作线程
     */
    private WorkThread[] workThreads;
    /**
     * 用队列做缓冲
     */
    private final BlockingQueue<Runnable> taskQueue;
    private final int worker_num;

    /**
     * 创建具有默认线程个数的线程池
     */
    public MyThreadPool() {
        this(WORK_NUM, TASK_COUNT);
    }

    /**
     * 创建线程池，worker_num为线程池中工作线程的个数
     * @param worker_num
     * @param taskCount
     */
    public MyThreadPool(int worker_num, int taskCount){
        if (worker_num <= 0) worker_num = WORK_NUM;
        if (taskCount <= 0) taskCount = TASK_COUNT;
        this.worker_num = worker_num;
        taskQueue = new ArrayBlockingQueue<>(taskCount);
        workThreads = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workThreads[i] = new WorkThread();
            workThreads[i].start();
        }
    }

    /**
     * 执行任务，其实只是把任务加入队列，什么时候执行有线程池管理决定
     * @param task
     */
    public void execute(Runnable task){
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 销毁线程池，该方法保证在所有任务都完成情况下才会销毁所有线程，否则等待任务完成才能销毁
     */
    public void destroy(){
        // 工作线程停止工作，且置为null
        System.out.println("ready close pool......");
        for (int i = 0; i < worker_num; i++) {
            workThreads[i].stopWorker();
            workThreads[i] = null; //help gc
        }
        taskQueue.clear(); //清空队列
    }

    /**
     * 覆盖toString
     * @return
     */
    @Override
    public String toString() {
        return "MyThreadPool{" +
                "workThread number =" + worker_num +
                ", wait task number=" + taskQueue.size() +
                '}';
    }

    /**
     * 内部类，工作线程
     */
    private class WorkThread extends Thread {

        @Override
        public void run() {
            Runnable r = null;
            try {
                while (!isInterrupted()) {
                    r = taskQueue.take();
                    if (r != null) {
                        System.out.println(getId() + " ready exec :"+r);
                        r.run();
                    }
                    //help gc
                    r = null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void stopWorker(){
            interrupt();
        }
    }
}
