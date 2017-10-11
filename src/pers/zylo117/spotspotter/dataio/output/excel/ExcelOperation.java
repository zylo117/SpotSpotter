package pers.zylo117.spotspotter.dataio.output.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.toolbox.Time;

public class ExcelOperation {
	public static Workbook createWookBook() {
//		Time.getTime();
//		String currrentPath = path + "/" + Time.year + "/" + Time.month;
//		FileOperation.createDir(currrentPath);
		// ����excel������
		final Workbook wb = new XSSFWorkbook();

		// ����sheet��ҳ��
		final Sheet sheet1 = wb.createSheet("Data");
		return wb;
	}

	public static void writeExcel2File(Workbook wb, String path) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (final IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}

		System.out.println("Data Recorded");
	}

	public static int getEmptyRow(Workbook wb, int sheetIndex, String cellRef) {
		final Sheet sheet = wb.getSheetAt(sheetIndex);
		final CellReference cellReference = new CellReference(cellRef); // һ����A1
		boolean flag = false;
		for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
			final Row r = sheet.getRow(i);
			if (r == null) {
				// ����ǿ��У���û���κ����ݡ���ʽ����ֱ�Ӱ������µ����������ƶ�
				sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
				continue;
			}
			flag = false;
			for (final Cell c : r) {
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
		final Sheet sheet = wb.getSheetAt(sheetIndex);
		final Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < content.size(); i++) {
			row.createCell(i).setCellValue(content.get(i));
		}
		return wb;
	}
	
	public static void getTrend(String processName){
		Time.getTime();
		final String path = System.getProperty("user.dir") + "/" + processName + "/" + Time.year + "/" + Time.month + "/"
				+ Time.day + ".xlsx";
		final File xlsx = new File(path);
		int rowIndex = 0;
		int failureCount = 0;
		if (xlsx.exists()) {
			InputStream iStream;
			Workbook wb;

			try {
				iStream = new FileInputStream(xlsx);

				wb = new XSSFWorkbook(iStream);
				rowIndex = ExcelOperation.getEmptyRow(wb, 0, "A1");

				for (int i = 1; i < rowIndex; i++) {
					final Sheet sheet = wb.getSheetAt(0);
					final Row row = sheet.getRow(i);
					final Cell cell = row.getCell(10);
					if (cell.getStringCellValue().equals("NG")) {
						failureCount++;
					}
				}
			} catch (final IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		// System.out.println(failureCount);
		// System.out.println(rowIndex);
		final BigDecimal bd_rate = new BigDecimal((double) failureCount * 100 / (rowIndex - 1));
		final double rate = bd_rate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
//		return rate;
	}

	public static void main(String[] args) {
//		writeExcel2File(new XSSFWorkbook(), "D:\\workspace\\SpotSpotter\\AA\\2017\\7\\31.xlsx");
	}
}
