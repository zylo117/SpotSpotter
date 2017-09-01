package pers.zylo117.spotspotter.dataoutput.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.toolbox.Time;

public class ExcelOperation {
	public static Workbook createWookBook() {
//		Time.getTime();
//		String currrentPath = path + "/" + Time.year + "/" + Time.month;
//		FileOperation.createDir(currrentPath);
		// 创建excel工作簿
		Workbook wb = new XSSFWorkbook();

		// 创建sheet（页）
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
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

	public static int getEmptyRow(Workbook wb, int sheetIndex, String cellRef) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		CellReference cellReference = new CellReference(cellRef); // 一般是A1
		boolean flag = false;
		for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
			Row r = sheet.getRow(i);
			if (r == null) {
				// 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
				sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
				continue;
			}
			flag = false;
			for (Cell c : r) {
				if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
					flag = true;
					break;
				}
			}
			if (flag) {
				i++;
				continue;
			} else {// 如果是空白行（即可能没有数据，但是有一定格式）
				if (i == sheet.getLastRowNum())// 如果到了最后一行，直接将那一行remove掉
					sheet.removeRow(r);
				else// 如果还没到最后一行，则数据往上移一行
					sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
			}
		}
		return sheet.getLastRowNum() + 1;
	}

	public static Workbook writeOneRow(Workbook wb, int sheetIndex, int rowIndex, List<String> content) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < content.size(); i++) {
			row.createCell(i).setCellValue(content.get(i));
		}
		return wb;
	}

	public static void main(String[] args) {
//		writeExcel2File(new XSSFWorkbook(), "D:\\workspace\\SpotSpotter\\AA\\2017\\7\\31.xlsx");
	}
}
