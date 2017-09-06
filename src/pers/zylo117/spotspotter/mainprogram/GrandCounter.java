package pers.zylo117.spotspotter.mainprogram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class GrandCounter {
	public static int totalTestQuantity;

	public static void readQTY() {
		String currrentPath = System.getProperty("user.dir") + "/" + "GrandCounter.txt";

		File counterTXT = new File(currrentPath);
		if (counterTXT.exists()) {
			InputStreamReader reader;
			try {
				reader = new InputStreamReader(new FileInputStream(counterTXT));

				StringBuilder result = new StringBuilder();
				try {
					BufferedReader br = new BufferedReader(reader);// 构造一个BufferedReader类来读取文件
					String s = null;
					while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
						result.append(System.lineSeparator() + s);
					}
					br.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				String s = result.toString();
				s = s.replace("\\s", "");
				s = s.replace("\r", "");
				s = s.replace(" ", "");
				s = s.replace("\n", "");
				totalTestQuantity = Integer.parseInt(s);
			} catch (FileNotFoundException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

		} else {
			try {
				FileOutputStream fileOut = new FileOutputStream(counterTXT);
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			totalTestQuantity = 0;
		}
	}

	public static void writeQTY() {
		String currrentPath = System.getProperty("user.dir") + "/" + "GrandCounter.txt";

		File counterTXT = new File(currrentPath);
		String str = Integer.toString(totalTestQuantity);
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

	public static void plusOne() {
		String currrentPath = System.getProperty("user.dir") + "/" + "GrandCounter.txt";

		File counterTXT = new File(currrentPath);
		if (counterTXT.exists()) {
			InputStreamReader reader;
			try {
				reader = new InputStreamReader(new FileInputStream(counterTXT));

				StringBuilder result = new StringBuilder();
				try {
					BufferedReader br = new BufferedReader(reader);// 构造一个BufferedReader类来读取文件
					String s = null;
					while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
						result.append(System.lineSeparator() + s);
					}
					br.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				String s = result.toString();
				s = s.replace("\\s", "");
				s = s.replace("\r", "");
				s = s.replace(" ", "");
				s = s.replace("\n", "");
				totalTestQuantity = Integer.parseInt(s);
			} catch (FileNotFoundException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

		} else {
			try {
				FileOutputStream fileOut = new FileOutputStream(counterTXT);
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			totalTestQuantity = 0;
		}

		totalTestQuantity++;
		String str = Integer.toString(totalTestQuantity);
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
}
