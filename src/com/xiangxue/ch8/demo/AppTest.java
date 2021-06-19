package com.xiangxue.ch8.demo;

import com.xiangxue.ch8.PendingJobPool;
import com.xiangxue.ch8.vo.TaskResult;
import com.xiangxue.tools.SleepTools;

import java.util.List;
import java.util.Random;

/**
 * @author liangya
 * @date 2021/6/17 17:01
 * 模拟一个应用程序，提交工作和任务，并查询任务进度
 */
public class AppTest {
    private final static String JOB_NAME = "计算数值";
    private final static int JOB_LENGTH = 1000;

    private static class QueryResult implements Runnable{
        private PendingJobPool pool;
        public QueryResult(PendingJobPool pool){
            super();
            this.pool = pool;
        }


        @Override
        public void run() {
            /*查询次数*/
            int i = 0;
            while (i < 350){
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if (!taskDetail.isEmpty()){
                    System.out.println(pool.getTaskProgress(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTools.ms(100);
                i++;
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
//        拿到框架实例
        PendingJobPool pool = PendingJobPool.getInstance();
//        注册job
        pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 1000*5);
        Random r = new Random();
        for (int i = 0; i < JOB_LENGTH; i++){
            //依次推入task
            pool.putTask(JOB_NAME, r.nextInt(1000));
        }
        Thread t = new Thread(new QueryResult(pool));
        t.start();
    }
}
