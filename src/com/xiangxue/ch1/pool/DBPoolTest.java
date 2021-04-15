package com.xiangxue.ch1.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:  //说是后面会学习那现在就写了
 * @create : 2021/1/12
 */
public class DBPoolTest {
    static DBPool pool = new DBPool(10);
    /**
     * 控制器：控制main线程将会等待所有worker结束后才能继续执行
     */
    static CountDownLatch end;
    public static void main(String[] args) throws InterruptedException {
        //线程数量
        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        //每个线程的操作次数
        int count = 20;
        //计数器：统计可以查询到连接的线程
        AtomicInteger got = new AtomicInteger();
        //计数器：统计没有拿到连接的线程
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new Worker(count, got, notGot),"worker_"+i);
            thread.start();
        }
        //main线程在此等待
        end.await();
        System.out.println("总共尝试了："+(threadCount * count));
        System.out.println("拿到连接的次数："+ got);
        System.out.println("没能连接的次数："+ notGot);
    }

    static class Worker implements Runnable{
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            while (count > 0){
                try {
                    //从线程池种获取连接，如果1000ms内无法获取到，将返回null
                    //分别统计连接获取的数量got和未能获取notGot
                    Connection connection = pool.fecthConn(1000);
                    if (connection != null){
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()+" 等待超时！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

}
