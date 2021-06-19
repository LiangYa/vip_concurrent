package com.xiangxue.ch8;

import com.xiangxue.ch8.vo.ITaskProcessor;
import com.xiangxue.ch8.vo.JobInfo;
import com.xiangxue.ch8.vo.TaskResult;
import com.xiangxue.ch8.vo.TaskResultType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author liangya
 * @date 2021/6/17 21:41
 *
 * 使用了自定义的线程池
 */
public class PendingJobPool {
    /*保守估计 不清楚时使用CPU的核心数*/
    private static final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
    /*任务队列*/
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);
    /*线程池 */
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_COUNTS, THREAD_COUNTS, 60,
            TimeUnit.SECONDS, taskQueue);
    /*job的存放容器*/
    private static ConcurrentHashMap<String, JobInfo<?>> jobInfoMap = new ConcurrentHashMap<>();

    private static CheckJobProcessor checkJob = CheckJobProcessor.getInstance();

    public static Map<String, JobInfo<?>> getMap() {
        return jobInfoMap;
    }

    /*单例模式*/
    private PendingJobPool(){}

    private static class JobPoolHolder{
        public static PendingJobPool pool = new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }
    /*单例模式*/

    /**
     * 对工作中的任务进行包装，提交给线程池使用，并处理任务的结果，写入缓存以供查询
     */
    private static class PendingTask<T,R> implements Runnable{
        private JobInfo<R> jobInfo;
        private T processData;

        public PendingTask(JobInfo<R> jobInfo, T processData){
            super();
            this.jobInfo = jobInfo;
            this.processData = processData;
        }

        @Override
        public void run() {
            R r = null;
            ITaskProcessor<T,R> taskProcessor = (ITaskProcessor<T,R>) jobInfo.getTaskProcessor();
            TaskResult<R> result = null;
            try {
                // 调用业务人员实现的具体方法
                result = taskProcessor.taskExecute(processData);
                // 要做检查，防止开发人员处理不当
                if (result == null){
                    result = new TaskResult<>(TaskResultType.EXCEPTION, r, "result is null!");
                }
                if (result.getResultType() == null){
                    if (result.getReason() == null){
                        result = new TaskResult<>(TaskResultType.EXCEPTION,r,"reason is null");
                    } else {
                        result = new TaskResult<>(TaskResultType.EXCEPTION,r,
                                "result is null, but reason:"+result.getReason());
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                result = new TaskResult<R>(TaskResultType.EXCEPTION,r,e.getMessage());
            } finally {
                jobInfo.addTaskResult(result,checkJob);
            }
        }
    }

    /**
     * 根据工作名称检索工作
     * @param jobName
     * @param <R>
     * @return
     */
    private <R> JobInfo<R> getJob(String jobName){
        JobInfo<R> jobInfo = (JobInfo<R>) jobInfoMap.get(jobName);
        if (null == jobInfo){
            throw new RuntimeException(jobName+"是个非法任务。");
        }
        return jobInfo;
    }

    /**
     * 调用者提交工作中的任务
     * @param jobName
     * @param t
     * @param <T>
     * @param <R>
     */
    public <T,R> void putTask(String jobName,T t){
        JobInfo<R> jobInfo = getJob(jobName);
        PendingTask<T,R> task = new PendingTask<T,R>(jobInfo,t);
        taskExecutor.execute(task);
    }

    /**
     * 调用者注册工作，如工作名，任务的处理器等等
     * @param jobName
     * @param jobLength
     * @param taskProcessor
     * @param expireTime
     * @param <R>
     */
    public <R> void registerJob(String jobName, int jobLength,
                                ITaskProcessor<?,?> taskProcessor,long expireTime){
        JobInfo<R> jobInfo = new JobInfo(jobName,jobLength,taskProcessor,expireTime);
        if (jobInfoMap.putIfAbsent(jobName,jobInfo) != null) {
            throw new RuntimeException(jobName+"已经注册了！");
        }
    }

    /**
     * 获得每个任务的处理详情
     * @param jobName
     * @param <R>
     * @return
     */
    public <R> List<TaskResult<R>> getTaskDetail(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTaskDetail();
    }

    public <R> String getTaskProgress(String jobName){
        JobInfo<R> jobInfo = getJob(jobName);
        return jobInfo.getTotalProcess();
    }
}
