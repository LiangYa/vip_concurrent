package com.xiangxue.ch7.transfer;

import com.xiangxue.ch7.transfer.service.ITransfer;
import com.xiangxue.ch7.transfer.service.SafeOperate;
import com.xiangxue.ch7.transfer.service.SafeOperateToo;
import com.xiangxue.ch7.transfer.service.TransferAccount;

/**
 * @author liangya
 * @date 2021/5/27 14:30
 */
public class PayCompany {
    private static class TransferThread extends Thread {
        //线程名字
        private String name;
        private UserAccount from;
        private UserAccount to;
        private int amount;
        //实际的转账动作
        private ITransfer transfer;

        public TransferThread(String name, UserAccount from, UserAccount to, int amount, ITransfer transfer) {
            super(name);
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                transfer.transfer(from,to,amount);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount zhangSan = new UserAccount("zhangsan", 2000);
        UserAccount lisi = new UserAccount("lisi", 2000);
        ITransfer transfer = new SafeOperateToo();
        TransferThread zhangsanTolisi = new TransferThread("zhangsanToLisi", zhangSan,lisi,2000,transfer);
        TransferThread lisiToZhangsan = new TransferThread("lisiToZhangsan",lisi,zhangSan,2000,transfer);
        zhangsanTolisi.start();
        lisiToZhangsan.start();
//        try {
//            zhangsanTolisi.join();
//            lisiToZhangsan.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("zhangsan: "+zhangSan.getMoney());
//        System.out.println("lisi: "+lisi.getMoney());

    }
}
