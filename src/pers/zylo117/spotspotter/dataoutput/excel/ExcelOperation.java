package pers.zylo117.spotspotter.dataoutput.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.toolbox.Time;

public class ExcelOperation {
	public static Workbook createWookBook(String path) {
		Time.getTime();
		String currrentPath = path + "/" + Time.year + "/" + Time.month;
		FileOperation.createDir(currrentPath);
		// ����excel������
		Workbook wb = new XSSFWorkbook();

		// ����sheet��ҳ��
		Sheet sheet1 = wb.createSheet("Data");
		return wb;
	}

	public static void writeExcel2File(Workbook wb, String path) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}

	public static Workbook writePropertyRow(Workbook wb, List<String> data) {
		Sheet sheet = wb.getSheetAt(0);
		Row propertyRow = sheet.createRow(0);
		List<String> header = new ArrayList<>();
		for (int i = 0; i < data.size(); i++) {
			propertyRow.createCell(i).setCellValue(data.get(i));
		}
		return wb;
	}
}
