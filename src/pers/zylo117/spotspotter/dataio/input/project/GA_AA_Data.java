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

public class GA_AA_Data {

	public static Workbook tmpWB = null;

	private static List<String> defineHeader() {
		final List<String> header = new ArrayList<>();
		header.add("NO");
		header.add("TestDate");
		header.add("ProcessName");
		header.add("ProductName");
		header.add("Lot");
		header.add("CarrierID");
		header.add("PocketNO");
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
		header.add("Offset");
		header.add("ROISize");
		return header;
	}

	public static String getProcess(Picture pic) {
		final String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		final String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		final int postfixLength = postfix.length();// 得到后缀名长度
		final String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}

	public static String getLot(Picture pic) {
		if (pic.processName.equals("GA")) {
			final String lot = pic.fileName.substring(23, 35);
			return lot;
		} else {
			return null;
		}
	}

	public static String getCarrierID(Picture pic) {
		if (pic.processName.equals("GA")) {
			final String carID = pic.fileName.substring(36, 46);
			return carID;
		} else
			return null;
	}

	public static String getPocketNO(Picture pic) {
		if (pic.processName.equals("GA")) {
			String PocketNO = pic.fileName.substring(47, 49);
			PocketNO = PocketNO.replace("_", "");
			return PocketNO;
		} else
			return null;
	}

	public static String getStation(Picture pic) {
		final int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 12, pic.fileParent.length() - 11);
	}

	public static String getTestDate() {
		Time.getTime();
		return Time.datetime_slash;
	}

	public static String getDay(Picture pic) {
		final int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 2, pic.fileParent.length());
	}

	public static String getMonth(Picture pic) {
		final int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 5, pic.fileParent.length() - 3);
	}

	public static String getYear(Picture pic) {
		final int length = pic.fileParent.length();
		return pic.fileParent.substring(pic.fileParent.length() - 10, pic.fileParent.length() - 6);
	}

	// public static void main(String[] args) {
	// try {
	// Picture pic = new
	// Picture("D:\\EpoxyInsp\\EW4\\2017\\08\\29\\015621(1)417_glue.jpg");
	// pic.fileName = "015621(1)417_glue.jpg";
	// pic.filePath = "D:\\EpoxyInsp\\EW4\\2017\\08\\29\\015621(1)417_glue.jpg\\";
	// System.out.println(getYear(pic));
	// System.out.println(getMonth(pic));
	// System.out.println(getDay(pic));
	// } catch (IOException e) {
	// // TODO 自动生成的 catch 块
	// e.printStackTrace();
	// }
	// }

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
		if (pic.processName.equals("AA")) {
			final String processDate = getYear(pic) + "/" + getMonth(pic) + "/" + getDay(pic) + " " + getHour(pic) + ":"
					+ getMinute(pic) + ":" + getSecond(pic);
			return processDate;
		} else if (pic.processName.equals("GA")) {
			final String year = pic.fileName.substring(0, 4);
			final String month = pic.fileName.substring(4, 6);
			final String day = pic.fileName.substring(6, 8);
			final String hour = pic.fileName.substring(9, 11);
			final String minute = pic.fileName.substring(11, 13);
			final String second = pic.fileName.substring(13, 15);
			final String processDate = year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second;
			return processDate;
		} else
			return null;
	}

	public static Map<Point, Double> getMax(Picture pic) {
		if (pic.failureData.isEmpty())
			return null;
		final List<Map<Point, Double>> maplist = pic.failureData;
		final List<Point> point = new ArrayList<>();
		final List<Double> value = new ArrayList<>();
		for (int i = 0; i < maplist.size(); i++) {
			Map dataMap = new HashMap<>();
			dataMap = maplist.get(i);
			final Set<Map.Entry<Point, Double>> entryseSet = dataMap.entrySet();
			for (final Map.Entry<Point, Double> entry : entryseSet) {
				final Point p = entry.getKey();
				final double v = entry.getValue();
				point.add(p);
				value.add(v);
			}
		}
		final double max = GetMaxMinMidAvg.getMaxFromList(value);
		final int maxIndex = value.indexOf(max);
		final Map<Point, Double> maxMap = new HashMap<>();
		maxMap.put(point.get(maxIndex), value.get(maxIndex));

		return maxMap;
	}

	public static void writeHeader(Picture pic) {
		final List<String> header = defineHeader();
		final String path = System.getProperty("user.dir");
		final String currrentPath = path + "/" + pic.processName + "/" + Time.year + "/" + Time.month;

		final Workbook wb = ExcelOperation.writeOneRow(ExcelOperation.createWookBook(), 0, 0, header);
		ExcelOperation.writeExcel2File(wb, currrentPath + "/" + Time.day + ".xlsx");
	}

	public static void writeNextRow(Picture pic, int sheetIndex, String cellRef, boolean ifOutputFile) {
		final List<String> content = new ArrayList<>();
		if (CentralControl.ifEngMode) {
			content.add(Integer.toString(GrandCounter.totalTestQuantity));
			content.add(getTestDate());
			content.add(pic.processName);
			content.add(CentralControl.productN);
		} else {
			content.add("");
			content.add("");
			content.add("");
			content.add("");
		}
		content.add(getLot(pic));
		content.add(getCarrierID(pic));
		content.add(getPocketNO(pic));
		content.add(Integer.toString(CentralControl.mcNO));
		if (pic.processName.equals("AA")) {
			content.add(getStation(pic));
		} else {
			content.add(null);
		}
		content.add(getProcessDate(pic));
		content.add(pic.result());
		if (pic.result().equals("NG")) {
			if (CentralControl.ifEngMode)
				content.add(pic.material);
			else
				content.add("");

			final Map<Point, Double> max = getMax(pic);
			for (final Entry<Point, Double> vo : max.entrySet()) {
				final Point p = vo.getKey();
				final double value = vo.getValue();

				content.add(Double.toString(p.x));
				content.add(Double.toString(p.y));
				if (CentralControl.ifEngMode)
					content.add(Double.toString(value));
				else
					content.add("");
			}
		} else {
			content.add("");
			content.add("");
			content.add("");
			content.add("");
		}

		if (CentralControl.ifEngMode) {
			content.add(pic.fileName);
			content.add(Integer.toString(CentralControl.binThresh));
			content.add(Integer.toString(CentralControl.ssThresh) + "%");
			content.add(Integer.toString(CentralControl.offset));
			content.add(Integer.toString(CentralControl.mosaicLength));
		}

		// 输出到Excel文件
		final String path = System.getProperty("user.dir");
		final String currrentPath = path + "/" + pic.processName + "/" + Time.year + "/" + Time.month;
		final String finalPath = currrentPath + "/" + Time.day + ".xlsx";

		final File xlsx = new File(finalPath);

		if (!xlsx.exists()) {
			FileOperation.createDir(currrentPath);
			Workbook wb = ExcelOperation.writeOneRow(ExcelOperation.createWookBook(), sheetIndex, 0, defineHeader());
			wb = ExcelOperation.writeOneRow(wb, sheetIndex, 1, content);

			if (ifOutputFile)
				ExcelOperation.writeExcel2File(wb, finalPath);
		} else {
			try {
				if (tmpWB == null) {
					final InputStream iStream = new FileInputStream(xlsx);
					tmpWB = new XSSFWorkbook(iStream);
				}
				final int rowIndex = ExcelOperation.getEmptyRow(tmpWB, sheetIndex, cellRef);
				tmpWB = ExcelOperation.writeOneRow(tmpWB, sheetIndex, rowIndex, content);

				if (ifOutputFile)
					ExcelOperation.writeExcel2File(tmpWB, finalPath);
			} catch (final IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
