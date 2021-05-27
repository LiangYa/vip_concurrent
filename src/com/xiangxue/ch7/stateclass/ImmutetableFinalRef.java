package com.xiangxue.ch7.stateclass;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liangya
 * @date 2021/5/26 15:50
 */
public class ImmutetableFinalRef {
    private final int a;
    private final int b;
    private final User user;

    public ImmutetableFinalRef(int a, int b) {
        this.a = a;
        this.b = b;
        user = new User(12);
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public User getUser() {
        return user;
    }

    public static class User{
        private int age;

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public User(int age) {
            this.age = age;
        }
    }

    public static void main(String[] args) {
        ImmutetableFinalRef ref = new ImmutetableFinalRef(12,23);
        User user = ref.getUser();
        user.setAge(35);

    }
}
