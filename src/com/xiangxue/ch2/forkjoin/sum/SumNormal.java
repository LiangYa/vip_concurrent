package com.xiangxue.ch2.forkjoin.sum;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/1/15
 */
public class SumNormal {
    public static void main(String[] args) {
        int count = 0;
        int[] src = MakeArray.makeArray();

        long start = System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count = count + src[i];
        }
        System.out.println("This count is "+ count
                + " spend time: "+ (System.currentTimeMillis() - start) + "ms");
    }
}
