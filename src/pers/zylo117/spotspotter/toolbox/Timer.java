package pers.zylo117.spotspotter.toolbox;

public class Timer {
	public static void waitFor(int msec) {
		// ��ʱ����
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ���� ms
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
