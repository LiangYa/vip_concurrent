package com.xiangxue.ch1.swn;

import java.util.LinkedList;
/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * 面试题：写一个固定容量同步容器，拥有put和get方法，以及getCount方法，
 *  * 能够支持2个生产者线程以及10个消费者线程的阻塞调用
 *  *
 *  * 使用wait和notify/notifyAll来实现
 * @create : 2021/1/23
 */
public class MyContainer<T> {
    final private LinkedList<T> container = new LinkedList<>();
    final private Integer MAX = 10;
    public Integer count = 0;

    public synchronized T get(){
        T t = null;
        while (container.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = container.removeLast();
        count--;
        this.notifyAll();
        return t;
    }

    public synchronized void put(T t){
        while (container.size() == MAX){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        container.addFirst(t);
        count++;
        this.notifyAll();
    }



}
