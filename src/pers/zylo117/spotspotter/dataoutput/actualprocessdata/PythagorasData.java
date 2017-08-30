package pers.zylo117.spotspotter.dataoutput.actualprocessdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import pers.zylo117.spotspotter.dataoutput.excel.ExcelOperation;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.toolbox.Time;

public class PythagorasData {
	public static String getType(Picture pic) {
		String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}

	public static String getHour(Picture pic) {
		return pic.fileName.substring(0, 2);
	}

	public static String getMinute(Picture pic) {
		return pic.fileName.substring(2, 4);
	}

	public static String getSecond(Picture pic) {
		return pic.fileName.substring(4, 6);
	}
	
	public static void main(String[] args){
		Time.getTime();
		System.out.println(Time.date_hyphen);
		String path = System.getProperty("user.dir");
		String currrentPath = path + "/" + Time.year + "/" + Time.month;
		
		List<String> data = new ArrayList<>();
		data.add("ProcessName");
		data.add("TestDate");
		data.add("MachineNO");
		data.add("Station");
		data.add("ProcessDate");
		data.add("TestResult"); // OK/NG
		data.add("PRResult"); // 怀疑的物质
		data.add("X");  // 最大值的坐标
		data.add("Y");
		data.add("Value");
		
		Workbook wb = ExcelOperation.writePropertyRow(ExcelOperation.createWookBook(path), data);
		ExcelOperation.writeExcel2File(wb, currrentPath + "/" +Time.day + ".xlsx");
	}
}
