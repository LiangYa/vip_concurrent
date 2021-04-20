package com.xiangxue.ch5;

/**
 * 使用位运算符控制权限
 * @author liangya
 * @date 2021/4/20 13:12
 */
public class Permission {
    public static final int SELECT = 1 << 0;
    public static final int UPDATE = 1 << 1;
    public static final int INSERT = 1 << 2;
    public static final int DELETE = 1 << 3;

    private int flag;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void delPermission(int per){
        flag = flag&(~per);
    }
    public void addPermission(int per){
        flag = flag|per;
    }

    public Boolean isNotAllow(int per){
        return ((flag&per) == 0);
    }
    public Boolean isAllow(int per){
        return ((flag&per) == per);
    }

    public static void main(String[] args) {
        Permission permission = new Permission();
        permission.setFlag(15);
        System.out.println("SELECT:"+permission.isAllow(Permission.SELECT));
        System.out.println("UPDATE:"+permission.isAllow(Permission.UPDATE));
        System.out.println("INSERT:"+permission.isAllow(Permission.INSERT));
        System.out.println("DELETE:"+permission.isAllow(Permission.DELETE));
        System.out.println("************************************");
        permission.delPermission(Permission.DELETE|Permission.SELECT);
        System.out.println("SELECT:"+permission.isAllow(Permission.SELECT));
        System.out.println("UPDATE:"+permission.isAllow(Permission.UPDATE));
        System.out.println("INSERT:"+permission.isAllow(Permission.INSERT));
        System.out.println("DELETE:"+permission.isAllow(Permission.DELETE));
        System.out.println("************************************");
        permission.addPermission(Permission.INSERT|Permission.UPDATE);
        System.out.println("SELECT:"+permission.isAllow(Permission.SELECT));
        System.out.println("UPDATE:"+permission.isAllow(Permission.UPDATE));
        System.out.println("INSERT:"+permission.isAllow(Permission.INSERT));
        System.out.println("DELETE:"+permission.isAllow(Permission.DELETE));
        System.out.println("************************************");
        permission.addPermission(Permission.DELETE|Permission.SELECT);
        System.out.println("SELECT:"+permission.isAllow(Permission.SELECT));
        System.out.println("UPDATE:"+permission.isAllow(Permission.UPDATE));
        System.out.println("INSERT:"+permission.isAllow(Permission.INSERT));
        System.out.println("DELETE:"+permission.isAllow(Permission.DELETE));
    }
}
