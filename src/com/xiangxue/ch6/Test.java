package com.xiangxue.ch6;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author liangya
 * @date 2021/5/7 16:40
 */
public class Test {
    public static void main(String[] args) {
        //wujieduilie
        Executors.newFixedThreadPool(3);
        Executors.newSingleThreadExecutor();
    }
}
