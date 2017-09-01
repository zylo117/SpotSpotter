package pers.zylo117.spotspotter.dataoutput.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections4.Get;
import org.apache.poi.hslf.blip.PICT;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.hslf.blip.Metafile.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.Point;

import pers.zylo117.spotspotter.dataoutput.excel.ExcelOperation;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.Time;

public class PythagorasData {

	private static List<String> defineHeader() {
		List<String> header = new ArrayList<>();
		header.add("ProcessName");
		header.add("ProductName");
		header.add("TestDate");
		header.add("MachineNO");
		header.add("Station");
		header.add("ProcessDate");
		header.add("TestResult"); // OK/NG
		header.add("PRResult"); // 怀疑的物质
		header.add("X"); // 最大值的坐标
		header.add("Y");
		header.add("Value");
		header.add("PictureName");
		return header;
	}

	public static String getType(Picture pic) {
		String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}

	public static String getTestDate() {
		Time.getTime();
		return Time.date_slash;
	}

	public static String getDay(Picture pic) {
		int length = pic.fileParent().length();
		int length_postfix = GetPostfix.fromFilename(pic.fileName).length();
		int length_name = pic.fileName.length();
		return pic.fileParent().substring(pic.fileParent().length()  - length_name-3,
				pic.fileParent().length()  - length_name-1);
	}

	public static String getMonth(Picture pic) {
		int length = pic.fileParent().length();
		int length_postfix = GetPostfix.fromFilename(pic.fileName).length();
		int length_name = pic.fileName.length();
		return pic.fileParent().substring(pic.fileParent().length()  - length_name - 6,
				pic.fileParent().length()  - length_name -4);
	}

	public static String getYear(Picture pic) {
		int length = pic.fileParent().length();
		int length_postfix = GetPostfix.fromFilename(pic.fileName).length();
		int length_name = pic.fileName.length();
		return pic.fileParent().substring(pic.fileParent().length()  - length_name - 11,
				pic.fileParent().length()  - length_name - 7);
	}
	
//	 public static void main(String[] args) {
//	 try {
//	 Picture pic = new
//	 Picture("D:\\EpoxyInsp\\EW4\\2017\\08\\29\\015621(1)417_glue.jpg");
//	 pic.fileName = "015621(1)417_glue.jpg";
//	 pic.filePath = "D:\\EpoxyInsp\\EW4\\2017\\08\\29\\015621(1)417_glue.jpg\\";
//	 System.out.println(getYear(pic));
//	 System.out.println(getMonth(pic));
//	 System.out.println(getDay(pic));
//	 } catch (IOException e) {
//	 // TODO 自动生成的 catch 块
//	 e.printStackTrace();
//	 }
//	 }

	public static String getHour(Picture pic) {
		return pic.fileName.substring(0, 2);
	}

	public static String getMinute(Picture pic) {
		return pic.fileName.substring(2, 4);
	}

	public static String getSecond(Picture pic) {
		return pic.fileName.substring(4, 6);
	}

	public static String getProcessDate(Picture pic) {
		String processDate = getYear(pic) + "/" + getMonth(pic) + "/" + getDay(pic) + " " + getHour(pic) + ":"
				+ getMinute(pic) + ":" + getSecond(pic);
		return processDate;
	}

	public static String getTestResult(Picture pic) {
		if (pic.failureData.isEmpty())
			return "OK";
		else
			return "NG";
	}

	public static Map<Point, Double> getMax(Picture pic) {
		if (pic.failureData.isEmpty())
			return null;
		List<Map<Point, Double>> maplist = pic.failureData;
		List<Point> point = new ArrayList<>();
		List<Double> value = new ArrayList<>();
		for (int i = 0; i < maplist.size(); i++) {
			Map dataMap = new HashMap<>();
			dataMap = maplist.get(i);
			Set<Map.Entry<Point, Double>> entryseSet = dataMap.entrySet();
			for (Map.Entry<Point, Double> entry : entryseSet) {
				Point p = entry.getKey();
				double v = entry.getValue();
				point.add(p);
				value.add(v);
			}
		}
		double max = GetMaxMinMidAvg.getMaxFromList(value);
		int maxIndex = value.indexOf(max);
		Map<Point, Double> maxMap = new HashMap<>();
		maxMap.put(point.get(maxIndex), value.get(maxIndex));

		return maxMap;
	}

	public static void writeHeader(Picture pic) {
		List<String> header = defineHeader();
		String path = System.getProperty("user.dir");
		String currrentPath = path + "/" + pic.processName + "/" + Time.year + "/" + Time.month;

		Workbook wb = ExcelOperation.writeOneRow(ExcelOperation.createWookBook(), 0, 0, header);
		ExcelOperation.writeExcel2File(wb, currrentPath + "/" + Time.day + ".xlsx");
	}

	public static void writeNextRow(Picture pic, int sheetIndex, String cellRef) {
		List<String> content = new ArrayList<>();
		content.add(pic.processName);
		content.add("NH");
		content.add(getTestDate());
		content.add("16");
		content.add("4");
		content.add(getProcessDate(pic));
		content.add(getTestResult(pic));
		if (getTestResult(pic).equals("NG")) {
			content.add("Glue/Dust");

			Map<Point, Double> max = getMax(pic);
			for (Entry<Point, Double> vo : max.entrySet()) {
				Point p = vo.getKey();
				double value = vo.getValue();

				content.add(Double.toString(p.x));
				content.add(Double.toString(p.y));
				content.add(Double.toString(value));
			}
		} else {
			content.add("");
			content.add("");
			content.add("");
			content.add("");
		}
		content.add(pic.fileName);
		String path = System.getProperty("user.dir");
		String currrentPath = path + "/" + pic.processName + "/" + Time.year + "/" + Time.month;
		String finalPath = currrentPath + "/" + Time.day + ".xlsx";

		File xlsx = new File(finalPath);

		if (!xlsx.exists()) {
			FileOperation.createDir(currrentPath);
			Workbook wb = ExcelOperation.writeOneRow(ExcelOperation.createWookBook(), sheetIndex, 0, defineHeader());
			wb = ExcelOperation.writeOneRow(wb, sheetIndex, 1, content);
			ExcelOperation.writeExcel2File(wb, finalPath);
		} else {
			try {
				InputStream iStream = new FileInputStream(xlsx);
				Workbook wb = new XSSFWorkbook(iStream);
				int rowIndex = ExcelOperation.getEmptyRow(wb, sheetIndex, cellRef);
				wb = ExcelOperation.writeOneRow(wb, sheetIndex, rowIndex, content);
				ExcelOperation.writeExcel2File(wb, finalPath);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}
}
