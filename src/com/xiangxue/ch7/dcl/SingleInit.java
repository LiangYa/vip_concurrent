package com.xiangxue.ch7.dcl;

public class SingleInit {
    public SingleInit() {
    }

    private static class InstanceHolder{
        public static SingleInit instance = new SingleInit();
    }

    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }
}
