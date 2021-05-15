package com.xiangxue.ch7.dcl;

public class SingleDcl {
    private static SingleDcl singleDcl;

    public SingleDcl() {
    }
    public static SingleDcl getInstance() {
        return singleDcl;
    }
}
