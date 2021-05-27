package com.xiangxue.ch7.stateclass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangya
 * @date 2021/5/26 15:51
 */
public class UnsafePublish {
    private List<Integer> list = new ArrayList<>(3);
    public UnsafePublish(){
        list.add(1);
        list.add(2);
        list.add(3);
    }
    public boolean isContains(int i) { return list.contains(i);}

    /**
     * 不安全，直接发布了list，其他调用拿到了list引用可以对list数据进行操作
     * @return
     */
    public List<Integer> getList() {
        return list;
    }

    /**
     * 加了锁
     * @param index
     * @return
     */
    public synchronized int getList(int index){
        return list.get(index);
    }

    public void set(int index, int val) {
        list.set(index, val);
    }
}
