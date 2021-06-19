package com.xiangxue.ch5.bq;


import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author liangya
 * @date 2021/6/17 20:20
 */
public class ItemVo<T> implements Delayed {
    /**
     * 到期时间，单位毫秒
     */
    private long activeTime;
    private T date;

    /**
     * activeTime是个过期时长
     * @param activeTime
     * @param date
     */
    public ItemVo(long activeTime, T date) {
        this.activeTime = activeTime;
        this.date = date;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public T getDate() {
        return date;
    }

    /**
     * 返回元素的剩余时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        long d = unit.convert(this.activeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        return d;
    }

    /**
     * 按照剩余时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (d==0)?0:((d>0)?1:-1);
    }
}
