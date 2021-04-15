package com.xiangxue.ch2.tools.semaphore;

import java.sql.Connection;
import java.util.Random;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/3/27
 */
public class AppTest {

    private static DBPoolSemaphore dbPool = new DBPoolSemaphore();

    private static class BusiThread extends Thread {
        @Override
        public void run() {
            //让每个线程持有连接的时间不一样
            Random r = new Random();
            long start = System.currentTimeMillis();
            try {
                Connection connect = dbPool.takeConnect();
                System.out.println("Thread_"+Thread.currentThread().getId()
                        + "_获取数据库连接共耗时【"+ (System.currentTimeMillis() - start)+"】ms。");
                Thread.sleep(100+r.nextInt(100));
                System.out.println("查询数据完成归还连接！");
                dbPool.returnConnect(connect);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Thread thread = new BusiThread();
            thread.start();
        }
    }
}
