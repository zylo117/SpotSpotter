package pers.zylo117.toolbox;

public class Timer {
	public static void timer(int msec) {
		// ��ʱ����
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ���� ms
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
