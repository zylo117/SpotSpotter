package pers.zylo117.spotspotter.dataio.output.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import pers.zylo117.spotspotter.toolbox.Time;

public class PythagorasEMail {
	public static void writeQTY() {
		String currrentPath = System.getProperty("user.dir") + "/" + "email_content.txt";

		File counterTXT = new File(currrentPath);
		Time.getTime();
		String str = "Date " + Time.date_slash + System.getProperty("line.separator")
					+ "Category " + "" + System.getProperty("line.separator")
					+ "AA " + "" + System.getProperty("line.separator")
					+ "SUT " + "" + System.getProperty("line.separator")
					+ "Result: continuously/discontinuously glue spilling";
		PrintWriter pfp;
		try {
			pfp = new PrintWriter(counterTXT);
			pfp.print(str);
			pfp.close();
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		writeQTY();
	}
}
