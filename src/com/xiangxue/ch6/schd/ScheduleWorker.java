package com.xiangxue.ch6.schd;

import com.xiangxue.tools.SleepTools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @auther liangya
 * @date 2021/5/7 20:43
 */
public class ScheduleWorker implements Runnable{
    /**
     * 普通任务类型
     */
    public final static int Normal = 0;
    /**
     * 会抛出异常的任务类型
     */
    public final static int HasException = -1;
    /**
     * 抛出异常但会捕捉的任务类型
     */
    public final static int ProcessException = 1;

    private int taskType;
    private Random r = new Random();

    public ScheduleWorker(int taskType) {
        this.taskType = taskType;
    }

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss");

    @Override
    public void run() {
        if (taskType == HasException) {
            System.out.println(format.format(new Date()) + " Exception made......");
            throw new RuntimeException("HasException Happen");
        }
        else if (taskType == ProcessException){
            try {
                SleepTools.ms(r.nextInt(1000));
                System.out.println(format.format(new Date())+" Exception made, but catch");
                throw new RuntimeException(" HasException Happen");
            } catch (RuntimeException e) {
                System.out.println(" Exception be caught");
            }
        }
        else {
            System.out.println(" Normal ...."+format.format(new Date()));
        }
    }
}
