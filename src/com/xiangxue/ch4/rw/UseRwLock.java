package com.xiangxue.ch4.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author liangya
 * @date 2021/4/20 14:24
 */
public class UseRwLock implements GoodsService{
    private GoodsInfo goodsInfo;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock getLock = lock.readLock();
    private final Lock setLock = lock.writeLock();

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public GoodsInfo getNum() {
        getLock.lock();
        try {
            Thread.sleep(5);
            return this.goodsInfo;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            getLock.unlock();
        }
        return null;
    }

    @Override
    public void setNum(int number) {
        goodsInfo.changeNumber(number);
    }

}
