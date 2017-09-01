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

	public static int getEmptyRow(Workbook wb, int sheetIndex, String cellRef) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		CellReference cellReference = new CellReference(cellRef); // һ����A1
		boolean flag = false;
		for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
			Row r = sheet.getRow(i);
			if (r == null) {
				// ����ǿ��У���û���κ����ݡ���ʽ����ֱ�Ӱ������µ����������ƶ�
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
			} else {// ����ǿհ��У�������û�����ݣ�������һ����ʽ��
				if (i == sheet.getLastRowNum())// ����������һ�У�ֱ�ӽ���һ��remove��
					sheet.removeRow(r);
				else// �����û�����һ�У�������������һ��
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
