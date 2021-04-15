package com.xiangxue.ch1.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池
 */
public class DBPool {
	
	private static LinkedList<Connection> pool =  new LinkedList<>();

	/**
	 * 初始化数据库连接池
	 * 
	 * @param initialSize
	 */
	public DBPool(int initialSize) {
		if (initialSize > 0) {
			for (int i = 0; i < initialSize; i++) {
				pool.addLast(SqlConnectionImpl.fecthConnection());
			}
		}
	}

	/**
	 * 取得数据库连接
	 * 在mills时间内拿不到连接，返回null
	 * @return
	 */
	public Connection fecthConn(long mills) throws InterruptedException {
		synchronized (pool){
			if (mills < 0){
				while (pool.isEmpty()){
					pool.wait();
				}
				return pool.removeFirst();
			} else {
				long overtime = System.currentTimeMillis() + mills;
				long remain = mills;
				while (pool.isEmpty() && remain > 0){
					pool.wait(remain);
					remain = overtime - System.currentTimeMillis();
				}
				Connection conn = null;
				if (!pool.isEmpty()){
					conn = pool.removeFirst();
				}
				return conn;
			}
		}
	}

	/**
	 * 释放连接
	 * 唤起其他等待进程
	 * @param conn
	 */
	public void releaseConn(Connection conn){
		if (conn != null){
			synchronized (pool){
				pool.addLast(conn);
				pool.notifyAll();
			}
		}
	}
}
