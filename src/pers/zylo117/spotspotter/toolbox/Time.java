package pers.zylo117.spotspotter.toolbox;

import java.util.Calendar;
import java.util.TimeZone;

public class Time {
	// ∏Ú +1s
	public static int year;
	public static int month;
	public static int day;
	public static int hour;
	public static int minute;
	public static int second;
	public static String date_slash;
	public static String date_hyphen;

	public static void getTime() {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		year = c1.get(Calendar.YEAR);
		month = c1.get(Calendar.MONTH) + 1;
		day = c1.get(Calendar.DATE);
		hour = c1.get(Calendar.HOUR_OF_DAY);
		minute = c1.get(Calendar.MINUTE);
		second = c1.get(Calendar.SECOND);
		date_slash = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
		date_hyphen = year + "-" + month + "-" + day + " " + hour + "-" + minute + "-" + second;
	}

	public static void waitFor(int msec) {
		// —” ±ª∫≥Â
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ∫¡√Î ms
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getTime();
		System.out.println(Time.year+" "+Time.month+" "+Time.day);
	}
}
