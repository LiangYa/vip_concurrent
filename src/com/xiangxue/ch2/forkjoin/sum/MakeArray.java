package com.xiangxue.ch2.forkjoin.sum;

import java.util.Random;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: 产生整形数据
 * @create : 2021/1/15
 */
public class MakeArray {
    /**
     * 数组长度
     */
    public static final int ARRAY_LENGTH = 4000;

    public static int[] makeArray(){
        //new一个随机数发生器
        Random r = new Random();
        int[] result = new int[ARRAY_LENGTH];
        for (int i = 0; i < ARRAY_LENGTH; i++){
            //用随机数填充数组
            result[i] = r.nextInt(ARRAY_LENGTH*3);
        }
        return result;
    }

//    public static void main(String[] args) {
//        Random random = new Random();
//        for (int i = 0; i < 10; i++){
//            System.out.println(random.nextInt(10));
//        }
//    }
}
