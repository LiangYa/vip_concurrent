package com.xiangxue.ch2.tools.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/3/27
 */
public class DBPoolSemaphore {
    private final static int POOL_SIZE = 10;
    /**
     * useful表示可用的数据库连接，useless表示已用的数据库连接  空闲的也是一种资源
     * */
    private final Semaphore useful, useless;

    public DBPoolSemaphore(){
        this.useful = new Semaphore(POOL_SIZE);
        this.useless = new Semaphore(0);
    }

    /**
     * 存放数据库连接的容器
     */
    private static LinkedList<Connection> pool = new LinkedList<>();

    static {
        for (int i = 0; i < POOL_SIZE; i++){
            pool.addFirst(SqlConnectImpl.fetchConnection());
        }
    }

    public void returnConnect(Connection connection) throws InterruptedException{
        if (connection != null){
            System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接！！"
                    +"可用连接数" + useful.availablePermits());
            useless.acquire();
            synchronized (pool) {
                pool.addLast(connection);
            }
            useful.release();
        }
    }

    public Connection takeConnect() throws InterruptedException{
        useful.acquire(); //会让线程阻塞
        Connection conn;
        synchronized (pool){
            conn = pool.removeFirst();
        }
        useless.release();
        return conn;
    }
}