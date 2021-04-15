package com.xiangxue.ch1.wn;

/**
 * 测试通知和唤醒 wait、notify notifyAll
 */
public class TestWN {
	private static Express express = new Express(0, Express.CITY);
	
	/**
	 * ���������仯���̣߳�������������
	 * �߳�һֱ�ȴ�
	 * @author Lya
	 *
	 */
	private static class CheckKm extends Thread{
		@Override
		public void run() {
			express.waitKm();
		}
	}
	/**
	 * ���ص�仯���̣߳��������������߳�һֱ�ȴ�
	 * @author Lya
	 *
	 */
	private static class CheckSite extends Thread{
		@Override
		public void run() {
			express.waitSite();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		//������ı仯
		for (int i = 0; i <3; i++) {
			new CheckKm().start();;
		}
		//��ݵص�仯
		for (int i = 0; i <3; i++) {
			new CheckSite().start();;
		}
		
		Thread.sleep(1000);
		//�����̱仯
		express.changeKm();
	}

}
