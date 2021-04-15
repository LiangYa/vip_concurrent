package com.xiangxue.ch1.wn;

/**
 *
 */
public class Express {
	public final static String CITY = "ShangHai";
	/**
	 * 公里数
	 */
	private int km;
	/**
	 * 站点数据
	 */
	private String site;
	public Express() {
	}
	public Express(int km, String site) {
		this.km = km;
		this.site = site;
	}
	
	/**
	 * �仯��������Ȼ��֪ͨ����wait״̬
	 * ����Ҫ�����������߳̽���ҵ����
	 * 
	 */
	public synchronized void changeKm() {
		this.km = 101;
		notifyAll();
	}
	
	/**
	 * �仯��������Ȼ��֪ͨ����wait״̬��
	 * ��Ҫ�����������߳̽���ҵ����
	 */
	public synchronized void changeSite() {
		this.site = "BeiJing";
		notifyAll();
	}
	
	public synchronized void waitKm() {
		while (this.km <= 100) {
			try {
				wait();
				System.out.println("Check km thread["+Thread.currentThread().getId()
						+"] is notified");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("the km is"+ this.km+",I will change db.");
	}
	
	public synchronized void waitSite() {
		while (CITY.equals(this.site)) {
			try {
				wait();
				System.out.println("Check site thread["+Thread.currentThread().getId()
						+"] is notified");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("the site is"+ this.site+",I will change db.");
	
	}
}
