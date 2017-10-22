package pers.zylo117.spotspotter.toolbox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckTime {
	public static boolean ifPastADay(String oldDate) {
		Time.getTime();
		final String newDate = Time.date_slash;
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			final Date nDate = df.parse(newDate);
			final Date oDate = df.parse(oldDate);
			if (nDate.getTime() > oDate.getTime()) {
				return true;
			} else if (nDate.getTime() < oDate.getTime()) {
				return false;
			} else {
				return false;
			}
		} catch (final Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) { 
		Time.waitFor(5000);
		final long beginTime = new Date().getTime();
		System.out.println(ifPastADay("2017/10/22"));
		final long endTime = new Date().getTime();
		System.out.println("Tact Time:[" + (endTime - beginTime) + "]ms");
	}
}
