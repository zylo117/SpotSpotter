package pers.zylo117.spotspotter.toolbox;

import java.util.Calendar;
import java.util.TimeZone;

public class Time {
	// ¸ò +1s
	public static int year;
	public static int month;
	public static int day;
	public static String strMonth;
	public static String strDay;
	public static int hour;
	public static int minute;
	public static int second;
	public static String datetime_slash;
	public static String date_slash;
	public static String datetime_hyphen;

	public static void getTime() {
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		year = c1.get(Calendar.YEAR);
		month = c1.get(Calendar.MONTH) + 1;
		day = c1.get(Calendar.DATE);
		hour = c1.get(Calendar.HOUR_OF_DAY);
		minute = c1.get(Calendar.MINUTE);
		second = c1.get(Calendar.SECOND);
		datetime_slash = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
		date_slash = year + "/" + month + "/" + day;
		datetime_hyphen = year + "-" + month + "-" + day + " " + hour + "-" + minute + "-" + second;
	
		if (month < 10)
			strMonth = "0" + month;
		else
			strMonth = Integer.toString(month);
		
		if (day < 10)
			strDay = "0" + day;
		else
			strDay = Integer.toString(day);
	}

	public static void waitFor(int msec) {
		// ÑÓÊ±»º³å
		try {
			Thread.currentThread();
			Thread.sleep(msec);// ºÁÃë ms
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		getTime();
		System.out.println(Time.year+" "+Time.month+" "+Time.day);
	}
}
