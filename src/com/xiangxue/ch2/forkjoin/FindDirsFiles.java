package com.xiangxue.ch2.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author : Lya
 * @program vip_concurrent
 * @Description: Fork/Join的异步用法同时不要求返回值：遍历指定目录（含子目录）
 *                寻找指定类型文件
 * @create : 2021/1/17
 */
public class FindDirsFiles extends RecursiveAction {

    /**
     * 当前任务需要搜寻的目录
     */
    private File path ;

    public FindDirsFiles(File file) {
        this.path = file;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> findDirsFiles = new ArrayList<>();
        File[] files = path.listFiles();
        if (files != null){
            for (File file : files) {
                if (file.isDirectory()){
                    findDirsFiles.add(new FindDirsFiles(file));
                } else {
                    if (file.getAbsolutePath().endsWith("doc")) {
                        System.out.println("文件："+file.getAbsolutePath());
                    }
                }
            }
            if (findDirsFiles.size() > 0){
                //invokeAll递交任务
                for (FindDirsFiles dirsFiles : invokeAll(findDirsFiles)){
                    //等待子任务执行完成
                    dirsFiles.join();
                }
            }
        }
    }

    public static void main(String[] args) {
        //用一个ForkJoinPool 实列调度总任务
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles findDirsFiles = new FindDirsFiles(new File("E:\\School\\Postgraduate"));
//        pool.invoke(findDirsFiles);是同步调用
        //异步调用
        pool.execute(findDirsFiles);
        System.out.println("Task is Running ......");

        int sum = 0;
        for (int i = 0; i < 2000; i++){
            sum += i;
        }
        System.out.println("The sum is "+ sum);

        //阻塞的方法
        findDirsFiles.join();
        System.out.println("Task  end");
    }
}
