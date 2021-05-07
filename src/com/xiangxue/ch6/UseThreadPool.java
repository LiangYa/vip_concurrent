package com.xiangxue.ch6;

import com.xiangxue.tools.SleepTools;
import javafx.concurrent.Worker;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liangya
 * @date 2021/5/7 16:55
 */
public class UseThreadPool {

    static class Worker implements Runnable{
        private String taskName;
        private Random r = new Random();

        public Worker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    +" process the task : " + taskName);
            SleepTools.ms(r.nextInt(100)*5);
        }
    }

    static class CallWorker implements Callable<String> {
        private String taskName;
        private Random r = new Random();

        public CallWorker(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskName() {
            return taskName;
        }

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()
                    +" process the task : " + taskName);
            return Thread.currentThread().getName()+":"+r.nextInt(100)*5;
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService pool = new ThreadPoolExecutor(2,4,3, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(10),
//                new ThreadPoolExecutor.DiscardOldestPolicy());
//        ExecutorService pool = Executors.newCachedThreadPool();
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 6; i++) {
            Worker worker = new Worker("worker_"+i);
            pool.execute(worker);
        }

        for (int i = 0; i < 6; i++) {
            CallWorker callWorker = new CallWorker("CallWorker_"+i);
            //submit是获取返回值
            Future<String> result = pool.submit(callWorker);
            System.out.println(result.get());
        }
        pool.shutdown();


    }
}
