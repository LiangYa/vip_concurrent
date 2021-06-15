package com.xiangxue.ch7.perfomance;

import java.util.HashSet;
import java.util.Set;

/**
 * @author liangya
 * @date 2021/6/15 15:33
 */
public class FinenessLock {
    public final Set<String> users = new HashSet<>();
    public final Set<String> queries = new HashSet<>();

    public void addUser(String name){
        synchronized (users){
            users.add(name);
        }
    }
    public void addQuery(String q){
        synchronized (queries){
            queries.add(q);
        }
    }

    public synchronized void removeUser(String u){
        users.remove(u);
    }

    public synchronized void removeQueries(String q){
        queries.remove(q);
    }
}
