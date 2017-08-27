package pers.zylo117.toolbox;

public class Timer {
	public static void waitFor(int msec) {
		// —” ±ª∫≥Â
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ∫¡√Î ms
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
