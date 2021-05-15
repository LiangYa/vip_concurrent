package com.xiangxue.ch7.dcl;

/**
 * 饿汉式
 */
public class SingleEHan {
    public static SingleEHan singleEHan = new SingleEHan();
    private SingleEHan(){}
}
