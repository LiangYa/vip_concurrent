package com.xiangxue.ch7;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author liangya
 * @date 2021/4/27 19:01
 */
public class ConcurrentSkipListMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        // 为什么上面这个不使用跳表
        // 空间利用率低

        ConcurrentSkipListMap map = new ConcurrentSkipListMap();
        // 查找，到第三次索引
        // 空间利用率更低

        ConcurrentSkipListSet set = new ConcurrentSkipListSet();
        //TreeMap和TreeSet
        //跳表 以空间换时间（类似索引）插入的时候取随机数，因为它维护了索引
        // 红黑树属于二叉树中的一种，

        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        //无界非阻塞队列，底层是个链表，遵循先进先出原则。
        // 但是他实现了并发控制

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(16);
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        //可以不设定初始大小，如果
    }
}
