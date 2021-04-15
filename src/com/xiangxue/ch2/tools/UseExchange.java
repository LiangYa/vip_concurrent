package com.xiangxue.ch2.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/3/28
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange = new Exchanger<>();

    public static void main(String[] args) {
        //第一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //存放数据的容器
                Set<String> setA = new HashSet<>();
                try {
                    //交换set
                    setA = exchange.exchange(setA);
                } catch (InterruptedException e) {

                }
            }
        }).start();
        //第二个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //存放数据的容器
                Set<String> setB = new HashSet<>();
                try {
                    /**
                     * set.add(...)
                     */
                    //交换set
                    setB = exchange.exchange(setB);
                } catch (InterruptedException e) {

                }
            }
        }).start();
    }
}
