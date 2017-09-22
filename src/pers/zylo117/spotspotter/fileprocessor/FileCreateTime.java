package pers.zylo117.spotspotter.fileprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.Time;

public class FileCreateTime {
	public static String getCreateTime(String filePath) {
		String postFix = GetPostfix.fromFilepath(filePath);
		String strTime = null;
		try {
			Process p = Runtime.getRuntime().exec("cmd /C dir " + filePath + "/tc");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.endsWith(postFix)) {
					strTime = line.substring(0, 17);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String year = strTime.substring(0, 4);
		// System.out.println("创建时间 " + strTime);
		return strTime;
		// 输出：创建时间 2009-08-17 10:21
	}

	public static boolean compare_date(String newDate, String oldDate) {

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date dt1 = df.parse(newDate);
			Date dt2 = df.parse(oldDate);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 在dt2前");
				return true;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1在dt2后");
				return false;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static boolean ifOutOfDate(String filePath) {
		String oldDate = getCreateTime(filePath);
		Time.getTime();
		String newDate = Time.date_slash;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date dt1 = df.parse(newDate);
			Date dt2 = df.parse(oldDate);
			if (dt1.getTime() > dt2.getTime()) {
				return true;
			} else if (dt1.getTime() < dt2.getTime()) {
				return false;
			} else {
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		getCreateTime("D:\\workspace\\SpotSpotter\\tmpIndex.dat");
		getCreateTime("D:\\workspace\\SpotSpotter\\Run.bat");
		System.out.println(compare_date(getCreateTime("D:\\workspace\\SpotSpotter\\tmpIndex.dat"),
				getCreateTime("D:\\workspace\\SpotSpotter\\Run.bat")));
		System.out.println(System.getProperty("user.dir") + "\\tmpIndex.dat");
		System.out.println(ifOutOfDate(System.getProperty("user.dir") + "\\tmpIndex.dat"));
	}
}
