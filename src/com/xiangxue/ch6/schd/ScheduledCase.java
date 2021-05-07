package com.xiangxue.ch6.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务
 * @auther liangya
 * @date 2021/5/7 20:58
 */
public class ScheduledCase {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
//        schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.ProcessException),1000,3000, TimeUnit.MILLISECONDS);
        schedule.scheduleWithFixedDelay(new ScheduleWorker(ScheduleWorker.ProcessException),1000,3000, TimeUnit.MILLISECONDS);
        System.out.println("********");

    }
}
