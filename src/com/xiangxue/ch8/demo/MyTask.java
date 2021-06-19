package com.xiangxue.ch8.demo;

import com.xiangxue.ch8.vo.ITaskProcessor;
import com.xiangxue.ch8.vo.TaskResult;
import com.xiangxue.ch8.vo.TaskResultType;
import com.xiangxue.tools.SleepTools;

import java.util.Random;

/**
 * @author liangya
 * @date 2021/6/18 17:52
 * 一个实际的任务类，将数值加上一个随机数，并休眠随机时间
 */
public class MyTask implements ITaskProcessor<Integer,Integer> {
    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        SleepTools.ms(flag);
        if (flag <= 300){
            //正常处理情况
            Integer returnValue = data.intValue()+flag;
            return new TaskResult<Integer>(TaskResultType.SUCCESS, returnValue);
        } else if (flag > 301 && flag <= 400){
            //处理失败情况
            return new TaskResult<>(TaskResultType.FAILURE, -1, "Failure");
        } else {
            //发生异常情况
            try {
                throw new RuntimeException("异常发生了！");
            } catch (Exception e){
                return new TaskResult<>(TaskResultType.EXCEPTION,-1,e.getMessage());
            }
        }
    }
}
