package com.xiangxue.ch6.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther liangya
 * @date 2021/5/8 10:45
 */
public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors()*10;

    public static void main(String[] args) {
        CompletionCase t = new CompletionCase();
        try {
            t.testByQueue();
            t.testByCompletion();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    /**
     * 方法一：自己写集合来实现获取线程池中任务的返回结果
     */
    public void testByQueue() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<>();
        //向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++){
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);
        }
        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            int sleepTime = queue.take().get();
            System.out.println(" slept "+sleepTime+" ms ...");
            count.addAndGet(sleepTime);
        }
        //关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+ count.get()
                +"ms,and spend time "
                +(System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法二，通过CompletionService来实现获取线程池中任务的返回结果
     * 可以选择现场醒了的执行，不用按照加入队列的执行顺序。
     */
    public void testByCompletion() throws Exception {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<Integer> cService = new ExecutorCompletionService<>(pool);
        //向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++){
            cService.submit(new WorkTask("ExecTask" + i));
        }
        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
            int sleepTime = cService.take().get();
            System.out.println(" slept "+sleepTime+" ms ...");
            count.addAndGet(sleepTime);
        }
        //关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+ count.get()
                +"ms,and spend time "
                +(System.currentTimeMillis() - start) + " ms");
    }


}

