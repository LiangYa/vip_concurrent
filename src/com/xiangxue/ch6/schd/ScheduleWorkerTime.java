package com.xiangxue.ch6.schd;

import com.xiangxue.tools.SleepTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * scheduleAtFixedRate
 * 和scheduleWithFixedDelay
 * @author liangya
 * @date 2021/5/7 17:53
 */
public class ScheduleWorkerTime implements Runnable{
    private final static int Long_8 = 8;
    private final static int Short_2 = 2;
    private final static int Normal_5 = 5;

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");
    public static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        if (count.get() == 0){
            System.out.println("Long_8......begin:" + format.format(new Date()));
            SleepTools.second(8);
            System.out.println("Long_8......end:" + format.format(new Date()));
            count.incrementAndGet();
        } else if (count.get() == 1){
            System.out.println("Short_2......begin:" + format.format(new Date()));
            SleepTools.second(2);
            System.out.println("Short_2......end:" + format.format(new Date()));
            count.incrementAndGet();
        } else if (count.get() == 2){
            System.out.println("Normal_5......begin:" + format.format(new Date()));
            SleepTools.second(5);
            System.out.println("Normal_5......end:" + format.format(new Date()));
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
//        scheduledExecutorService.execute(new ScheduleWorkerTime());
        scheduledExecutorService.scheduleAtFixedRate(new ScheduleWorkerTime(),0,6000, TimeUnit.MILLISECONDS);
//        scheduledExecutorService.scheduleWithFixedDelay(new ScheduleWorkerTime(),0,6000, TimeUnit.MILLISECONDS);

    }
}
