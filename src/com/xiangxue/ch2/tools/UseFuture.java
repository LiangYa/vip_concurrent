package com.xiangxue.ch2.tools;

import java.util.concurrent.Callable;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/3/28
 */
public class UseFuture {
    private static class UseCallable implements Callable<Integer> {
        private int sum;

        @Override
        public Integer call() {
            System.out.println("");
            return null;
        }
    }
}
