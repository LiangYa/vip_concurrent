package com.xiangxue.ch1.swn;

import java.util.concurrent.*;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/1/23
 */
public class TestMyContainer {
    public static void main(String[] args) {
        MyContainer<String> container = new MyContainer();
        //消费者
        for (int i = 0; i < 10; i++){
            new Thread(()->{
                for (int j = 0; j < 5; j++){
                    System.out.println("消费线程："+Thread.currentThread().getName()+"消费："+container.get());
                }
            }, "c"+i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("睡两秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //生产者
        for (int j = 0; j < 2; j++){
            new Thread(()->{
                for (int i = 0; i < 25; i++){
                    container.put("生产"+i);
                    System.out.println("生产线程："+Thread.currentThread().getName()+"生产"+i);
                }
            },"p"+j).start();
        }
    }
}
