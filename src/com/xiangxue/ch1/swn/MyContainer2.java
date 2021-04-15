package com.xiangxue.ch1.swn;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 *  * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *  * 使用Lock和Condition来实现 (可重入锁)
 *  * 对比两种方式，Condition的方式可以更加精确的指定哪些线程被唤醒
 * @create : 2021/1/23
 */
public class MyContainer2<T> {
    private ReentrantLock lock = new ReentrantLock();
    private Condition consumer = lock.newCondition();
    private Condition producer = lock.newCondition();
    private LinkedList<T> container = new LinkedList<>();
    private Integer MAX = 10;
    private Integer count = 0;

    public T get(){
        T t = null;
        try {
            lock.lock();
            while (count == 0) {
                consumer.await();
            }
            count--;
            t = container.removeLast();
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public void put(T t){
        try {
            lock.lock();
            while (count >= MAX) {

                producer.await();
            }
            container.addFirst(t);
            count++;
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
