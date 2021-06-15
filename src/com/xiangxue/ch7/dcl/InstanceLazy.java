package com.xiangxue.ch7.dcl;

public class InstanceLazy {
    private Integer value;
    /**
     * 巨型数组；
     */
    private Integer val;

    public InstanceLazy(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    private static class ValHolder {
        public static Integer vHolder = new Integer(100000000);
    }

    public Integer getVal(){
        return ValHolder.vHolder;
    }
}
