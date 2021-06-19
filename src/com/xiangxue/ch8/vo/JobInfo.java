package com.xiangxue.ch8.vo;

import com.xiangxue.ch8.CheckJobProcessor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liangya
 * @date 2021/6/17 19:12
 */
public class JobInfo<R> {
    /**
     * 区分唯一的工作
     */
    private final String jobName;
    /**
     * 工作的任务个数
     */
    private final int jobLength;
    /**
     * 这个工作的任务处理器
     */
    private final ITaskProcessor<?,?> taskProcessor;
    /**
     * 成功处理的任务数
     */
    private final AtomicInteger successCount;
    /**
     * 已经处理的任务数据
     */
    private final AtomicInteger taskProcessorCount;
    /**
     * 结果队列，拿结果从头拿，放结果从尾部放
     */
    private final LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;
    /**
     * 工作的完成保存的时间，超过这个时间从缓存中清除
     */
    private final long expireTime;

    /**
     * 修改为
     * 阻塞队列不应该由调用者传入，应该由内部生成，长度为工作的任务个数
     * @param jobName
     * @param jobLength
     * @param taskProcessor
     * @param expireTime
     */
    public JobInfo(String jobName, int jobLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcessor = taskProcessor;
        this.successCount = new AtomicInteger(0);
        this.taskProcessorCount = new AtomicInteger(0);
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.expireTime = expireTime;
    }

    public ITaskProcessor<?,?> getTaskProcessor(){
        return taskProcessor;
    }

    /*返回成功处理的结果数*/
    public int getSuccessCount(){
        return successCount.get();
    }

    /*返回当前已处理的结果数*/
    public int getTaskProcessorCount(){
        return taskProcessorCount.get();
    }

    /*提供工作中失败的次数，课堂上没有加，只是为了方便调用者使用*/
    public int getFailCount(){
        return taskProcessorCount.get() - successCount.get();
    }

    public String getTotalProcess(){
        return "Success["+successCount.get()+"}/Current["
                +taskProcessorCount.get()+"] Total["+jobLength+"]";
    }

    /**
     * 获得工作中每个任务的处理详情
     * 安全的发布
     * @return
     */
    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> taskList = new LinkedList<>();
        TaskResult<R> taskResult;
//        从阻塞队列中拿到任务结果，反复取，一直到null为止，说明目前的队列中最新的任务结果已经取完了，可以不取了
        while ((taskResult = taskDetailQueue.pollFirst()) != null){
            taskList.add(taskResult);
        }
        return taskList;
    }

    /**
     * 放任务的结果，从业务应用角度来说活，保证最终一致性即可，不需要对方法加锁
     * 有可能出现队列和计数不一致
     * 从业务角度来说活，只需要保障最终一致
     * @param result
     * @param checkJob
     */
    public void addTaskResult(TaskResult<R> result, CheckJobProcessor checkJob){
        if (TaskResultType.SUCCESS.equals(result.getResultType())){
            successCount.incrementAndGet();
        }
        taskDetailQueue.addLast(result);
        taskProcessorCount.incrementAndGet();
        if (taskProcessorCount.get() == jobLength){
            checkJob.putJob(jobName,expireTime);
        }
    }
}
