package com.xiangxue.ch7.transfer.service;

import com.xiangxue.ch7.transfer.UserAccount;

/**
 * @author liangya
 * @date 2021/5/27 14:31
 * 使用jdk提供的hash计算 比较出顺序
 * 也可以使用实体类中有一个唯一的ID可以去比较顺序
 */
public class SafeOperate implements ITransfer{
    //tie：平局
    private static Object tieLock = new Object();
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        //先锁hash小的
        if (fromHash < toHash){
            //先锁转出
            synchronized (from){
                System.out.println(Thread.currentThread().getName()+" 1get"+from.getName());
                Thread.sleep(100);
                //再锁转入
                synchronized (to){
                    System.out.println(Thread.currentThread().getName()+ " get"+to.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        } if (fromHash > toHash){
            synchronized (to){
                System.out.println(Thread.currentThread().getName()+" get"+to.getName());
                Thread.sleep(100);
                synchronized (from){
                    System.out.println(Thread.currentThread().getName()+" get"+from.getName());
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        } else {
            //解决hash冲突
            synchronized (tieLock){
                synchronized (from){
                    synchronized (to){
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }

    }
}
