package com.xiangxue.ch8.vo;

/**
 * @author liangya
 * @date 2021/6/17 17:02
 */
public enum  TaskResultType {
    //方法成功执行并返回了业务人员需要的结果
    SUCCESS,
    //方法成功执行并返回了业务人员不需要的结果
    FAILURE,
    //方法执行抛出异常
    EXCEPTION
}
