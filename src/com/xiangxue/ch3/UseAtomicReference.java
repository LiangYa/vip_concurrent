package com.xiangxue.ch3;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: 演示引用类型
 * @create : 2021/4/11
 */
public class UseAtomicReference {
    static AtomicReference<UserInfo> userRef = new AtomicReference<>();
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("Mark", 15);
        userRef.set(userInfo);
        UserInfo updateUser = new UserInfo("Bill", 17);
        //更新的是原子类的数据
        userRef.compareAndSet(userInfo, updateUser);
        System.out.println(userRef.get().getName());
        System.out.println(userRef.get().getAge());
        System.out.println(userInfo.getAge());
        System.out.println(userInfo.getName());
    }

    /**
     * 定义一个实例
     */
    static class UserInfo {
        private String name;
        private int age;
        public UserInfo(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
