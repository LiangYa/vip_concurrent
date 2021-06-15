package com.xiangxue.ch7.transfer.service;

import com.xiangxue.ch7.transfer.UserAccount;
import com.xiangxue.tools.SleepTools;

import java.util.Random;

/**
 * @author liangya
 * @date 2021/5/27 14:31
 * 不会产生死锁的第二种方法
 * 显示锁
 */
public class SafeOperateToo implements ITransfer{

    @Override
    public void transfer(UserAccount from, UserAccount to, int amount)
            throws InterruptedException {
        Random random = new Random();
        while (true){
            if (from.getLock().tryLock()){
                try {
                    System.out.println(Thread.currentThread().getName()+" get"+from.getName());
                    if (to.getLock().tryLock()){
                        try {
                            System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        }finally {
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            //活锁随机数  错开多个线程拿锁的时间 tryLock拿不到全部资源会释放现在持有的资源
            SleepTools.ms(random.nextInt(10));
        }
    }
}
