package com.xiangxue.ch7.transfer.service;

import com.xiangxue.ch7.transfer.UserAccount;

/**
 * @author liangya
 * @date 2021/5/27 14:31
 * 不安全的转账动作的实现
 */
public class TransferAccount implements ITransfer{

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        //先锁转出
        synchronized (from) {
            System.out.println(Thread.currentThread().getName()+" 1get"+from.getName());
            Thread.sleep(100);
            //再锁转入
            synchronized (to) {
                System.out.println(Thread.currentThread().getName()+" 2get"+from.getName());
                from.flyMoney(amount);
                to.addMoney(amount);
            }
        }
    }
}
