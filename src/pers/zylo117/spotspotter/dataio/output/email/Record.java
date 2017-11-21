package pers.zylo117.spotspotter.dataio.output.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.dataio.input.project.GA_AA_Data;
import pers.zylo117.spotspotter.dataio.output.excel.ExcelOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.toolbox.Time;

public class Record {

	public static double failureRate(String assas) {
		// Time.getTime();
		// final String path = System.getProperty("user.dir") + "/" + processName + "/"
		// + Time.year + "/" + Time.month
		// + "/" + Time.day + ".xlsx";
		// final File xlsx = new File(path);
		int rowIndex = 0;
		int failureCount = 0;
		
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(assas);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		if (wb == null)
			return 0;
		else {
			rowIndex = ExcelOperation.getEmptyRow(wb, 0, "A1");

			for (int i = 1; i < rowIndex; i++) {
				final Sheet sheet = wb.getSheetAt(0);
				final Row row = sheet.getRow(i);
				final Cell cell = row.getCell(10);
				if (cell.getStringCellValue().equals("NG")) {
					failureCount++;
				}
			}
			// System.out.println(failureCount);
			// System.out.println(rowIndex);
			final BigDecimal bd_rate = new BigDecimal((double) failureCount * 100 / (rowIndex - 1));
			final double rate = bd_rate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			return rate;
		}
	}

	public static void main(String[] args) throws IOException {
		
		for(int i = 28; i<29;i++ ) {
			String path = "\\\\43.98.80.42\\userdata$\\5117005569\\ProjectArgus\\SpotSpotter\\GA\\2017\\10\\";
			path = path + i + ".xlsx";
			double fr = failureRate(path);
			 System.out.println(fr);
		}
		
		 
	}
}
