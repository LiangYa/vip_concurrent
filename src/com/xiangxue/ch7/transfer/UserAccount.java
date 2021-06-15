package com.xiangxue.ch7.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liangya
 * @date 2021/5/27 14:30
 */
public class UserAccount {
    private final String name;
    private int money;
    //显示锁
    private final Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }
    //上面这个代码是在这个账户拿显示锁

    public UserAccount(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    /**
     * 转入资金
     * @param amount
     */
    public void addMoney(int amount) {
        money = money + amount;
    }

    /**
     * 转出资金
     * @param amount
     */
    public void flyMoney(int amount) {
        money = money - amount;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", lock=" + lock +
                '}';
    }

}
