package com.xiangxue.ch7.stateclass;

/**
 * @author liangya
 * @date 2021/5/26 15:50
 * 不可变的类
 */
public class ImmutetableFinal {
    private final int a;
    private final int b;

    public ImmutetableFinal(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }
}
