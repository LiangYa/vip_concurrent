package com.xiangxue.ch2.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/3/25
 */
public class UseCyclicBarrier {
    private static CyclicBarrier barrier = new CyclicBarrier(5);
    /**
     * 存放子线程工作结果的容器
     */
    private static ConcurrentHashMap<String,Long> resultMap =
            new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i=0; i <=4; i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }
    }

    private static class CollectThread implements Runnable{
        @Override
        public void run() {
            StringBuffer result = new StringBuffer();
            for (Map.Entry<String,Long> workResult : resultMap.entrySet()){
                result.append("["+workResult.getValue()+"]");
            }
            System.out.println(" the result = "+ result);
            System.out.println("do other business....");
        }
    }

    /**
     * 工作线程
     */
    private static class SubThread implements Runnable{
        @Override
        public void run() {
            //线程本身的处理结果
            long id = Thread.currentThread().getId();
            resultMap.put(Thread.currentThread().getId()+"",id);
            //随机决定工作线程是否睡眠
            Random r = new Random();
            try {
                if (r.nextBoolean()) {
                    Thread.sleep(1000 + id);
                    System.out.println("Thread_"+id+"....do something ");
                }
                //拦截
                barrier.await();
                Thread.sleep(1000+id);
                System.out.println("Thread_"+id+"....do its business ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
