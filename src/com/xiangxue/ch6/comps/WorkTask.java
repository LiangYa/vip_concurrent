package com.xiangxue.ch6.comps;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @auther liangya
 * @date 2021/5/8 10:44
 */
public class WorkTask implements Callable<Integer> {

    private String name;


    public WorkTask(String name) {
        this.name = name;
    }

    public WorkTask() {
    }

    @Override
    public Integer call() throws Exception {
        int sleepTime = new Random().nextInt(1000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sleepTime;
    }
}
