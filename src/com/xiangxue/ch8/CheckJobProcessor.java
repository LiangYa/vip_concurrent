package com.xiangxue.ch8;

import com.xiangxue.ch5.bq.ItemVo;

import java.util.concurrent.DelayQueue;

/**
 * @author liangya
 * @date 2021/6/17 20:18
 */
public class CheckJobProcessor {
    /**
     * 存放已完成任务等待过期的队列
     */
    private static DelayQueue<ItemVo<String>> queue = new DelayQueue<>();

    /** 单例模式---------- */
    private CheckJobProcessor(){}

    private static class ProcessorHolder{
        public static CheckJobProcessor processor = new CheckJobProcessor();
    }

    public static CheckJobProcessor getInstance(){
        return ProcessorHolder.processor;
    }
    /** 单例模式—————————— */


    /**
     * 处理队列中到期任务的实行
     */
    private static class FetchJob implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    //拿到已经过期的任务
                    ItemVo<String> item = queue.take();
                    String jobName = item.getDate();
                    PendingJobPool.getMap().remove(jobName);
                    System.out.println(jobName+" is out of date,remove from map!");
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobName, long expireTime){
        ItemVo<String> item = new ItemVo<>(expireTime,jobName);
        queue.offer(item);
        System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime);
    }

    static {
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程。。。。。。");
    }
}
