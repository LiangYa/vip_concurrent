package com.xiangxue.ch2.forkjoin.sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: Fork/Join的同步用法同时演示返回结果值：统计整形数组中所有元素的和。
 * @create : 2021/1/15
 */
public class SumArray {
    private static class SumTask extends RecursiveTask<Integer>{
        private final static int THRESHOLD = MakeArray.ARRAY_LENGTH/10;
        /**
         * 表示我们要实际统计的数组
         */
        private int[] src;
        /**
         * 开始统计的下标
         */
        private int fromIndex;
        /**
         * 统计到哪里结束的下标
         */
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex-fromIndex < THRESHOLD) {
                int count = 0;
                for (int i = fromIndex; i <= toIndex; i++){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count = count + src[i];
                }
                return count;
            }else {
                //fromIndex...mid..toIndex
                int mid = (toIndex + fromIndex)/2;
//                int mid = (toIndex - fromIndex)/2 + fromIndex;
                SumTask left = new SumTask(src,fromIndex,mid);
                SumTask right = new SumTask(src,mid+1,toIndex);
                invokeAll(left,right);
                return left.join() + right.join();
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int[] src = MakeArray.makeArray();

        SumTask innerFind = new SumTask(src, 0, src.length -1);
        long start = System.currentTimeMillis();

        pool.invoke(innerFind);
        System.out.println("Task is Running ........");

        System.out.println("The count is " + innerFind.join()
        +" spend time:" + (System.currentTimeMillis()-start)+"ms");
    }
}
