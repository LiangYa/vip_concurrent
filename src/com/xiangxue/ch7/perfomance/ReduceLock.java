package com.xiangxue.ch7.perfomance;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author liangya
 * @date 2021/6/15 15:14
 * 缩小锁的范围
 * 正则和复杂计算耗费CPU
 */
public class ReduceLock {
    private Map<String,String> matchMap = new HashMap<>();

    public synchronized boolean isMatch(String name, String regexp){
        String key = "user."+name;
        String job = matchMap.get(key);
        if (job == null){
            return false;
        } else {
            //很耗费时间
            return Pattern.matches(regexp,job);
        }
    }

    public boolean isMatchReduce(String name, String regexp){
        String key = "user."+name;
        String job;
        synchronized (this){
            job = matchMap.get(key);
        }
        if (job == null){
            return false;
        } else {
            //很耗费时间
            return Pattern.matches(regexp,job);
        }
    }
}
