package com.xiangxue.ch3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description:
 * @create : 2021/4/11
 */
public class AtomicArray {
    static int[] arr = new int[]{1,4};
    static AtomicIntegerArray atomicArray = new AtomicIntegerArray(arr);

    public static void main(String[] args) {
        atomicArray.set(0,2);
        atomicArray.compareAndSet(0,1,3);
        System.out.println(atomicArray.get(0));
        System.out.println(arr[0]);
    }
}
