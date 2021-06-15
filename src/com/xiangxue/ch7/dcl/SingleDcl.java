package com.xiangxue.ch7.dcl;

public class SingleDcl {
    private volatile static SingleDcl singleDcl;

    public SingleDcl() {
    }
    public static SingleDcl getInstance() {
        if (singleDcl == null){
            synchronized (SingleDcl.class){
                if (singleDcl == null){
                    singleDcl = new SingleDcl();
                }
            }
        }
        return singleDcl;
    }
}
