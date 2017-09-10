package pers.zylo117.spotspotter.dataio.input.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.Point;

import pers.zylo117.spotspotter.dataio.output.excel.ExcelOperation;
import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.mainprogram.GrandCounter;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.Time;

public class PythagorasData {

	private static List<String> defineHeader() {
		List<String> header = new ArrayList<>();
		header.add("NO");
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
		header.add("BinarizationThreshold");
		header.add("SpotSpotterThreshold");
		return header;
	}

	public static String getProcess(Picture pic) {
		String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
	
	public static String getStation(Picture pic) {
		int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 12, pic.fileParent.length() - 11);
	}

	public static String getTestDate() {
		Time.getTime();
		return Time.date_slash;
	}

	public static String getDay(Picture pic) {
		int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 2, pic.fileParent.length());
	}

	public static String getMonth(Picture pic) {
		int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 5, pic.fileParent.length() - 3);
	}

	public static String getYear(Picture pic) {
		int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 10, pic.fileParent.length() - 6);
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
		content.add(Integer.toString(GrandCounter.totalTestQuantity));
		content.add(pic.processName);
		content.add(CentralControl.productN);
		content.add(getTestDate());
		content.add(Integer.toString(CentralControl.mcNO));
		content.add(getStation(pic));
		content.add(getProcessDate(pic));
		content.add(pic.result());
		if (pic.result().equals("NG")) {
			content.add(pic.material);

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
		content.add(Integer.toString(CentralControl.binThresh));
		content.add(Integer.toString(CentralControl.ssThresh) + "%");
		
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
		System.out.println("Data Recorded");
	}
}
