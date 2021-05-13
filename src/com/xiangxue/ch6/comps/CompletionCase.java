package com.xiangxue.ch6.comps;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther liangya
 * @date 2021/5/8 10:45
 */
public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors()*10;

    public static void main(String[] args) {
        CompletionCase t = new CompletionCase();
        t.testByQueue();
        t.testByCompletion();
    }

    /**
     * 方法一：自己写集合来实现获取线程池中任务的返回结果
     */
    public void testByQueue() {
        long start = System.currentTimeMillis();
        AtomicInteger count = new AtomicInteger(0);
    }

    public void testByCompletion() {
    }


}

