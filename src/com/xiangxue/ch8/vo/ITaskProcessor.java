package com.xiangxue.ch8.vo;

/**
 * 要求框架使用者的实现任务的接口，因为任务的性质在调用时才知道，
 * 所以传入的参数和方法的返回值均使用泛型
 * @param <T>
 * @param <R>
 */

public interface ITaskProcessor<T,R> {
    /**
     *
     * @param data 调用方法使用的业务数据
     * @return 方法执行后业务方法的结果
     */
    TaskResult<R> taskExecute(T data);
}
