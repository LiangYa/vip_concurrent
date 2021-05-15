package com.xiangxue.ch6.comps;

/**
 * @author liangya
 * @date 2021/5/15 10:25
 */
public class WorkTaskTwo implements Runnable{
    private String name;
    private int sleepTime = 0;

    public WorkTaskTwo(String name, int sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {

    }
}
