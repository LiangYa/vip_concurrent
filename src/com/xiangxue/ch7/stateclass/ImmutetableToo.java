package com.xiangxue.ch7.stateclass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liangya
 * @date 2021/5/26 15:50
 * 不可变的类
 */
public class ImmutetableToo {
    private List<Integer> list = new ArrayList<>(3);
    public ImmutetableToo(){
        list.add(1);
        list.add(2);
        list.add(3);
    }
    public boolean isContains(int i) { return list.contains(i);};
}
