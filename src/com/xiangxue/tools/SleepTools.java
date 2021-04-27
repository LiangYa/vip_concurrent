package com.xiangxue.tools;

import java.util.concurrent.TimeUnit;

/**
 * @author liangya
 * @date 2021/4/27 16:45
 * 跟着老师敲一个睡眠的工具
 */
public class SleepTools {
    /**
     * 按照秒休眠
     */
    public static final void second(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照毫秒休眠
     */
    public static final void ms(int seconds){
        try {
            TimeUnit.MILLISECONDS.sleep(seconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
