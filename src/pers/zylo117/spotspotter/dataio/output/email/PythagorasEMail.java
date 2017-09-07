package pers.zylo117.spotspotter.dataio.output.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.dataio.input.project.PythagorasData;
import pers.zylo117.spotspotter.dataio.output.excel.ExcelOperation;
import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.toolbox.Time;

public class PythagorasEMail {
	public static void writeEMail() throws IOException {
		String currrentPath = System.getProperty("user.dir") + "/" + "email_content.txt";

		File counterTXT = new File(currrentPath);
		Time.getTime();
		String str = "Date " + Time.date_slash + System.getProperty("line.separator") + "Category "
				+ CentralControl.productN + System.getProperty("line.separator")
				// + "AA " + Integer.toString(CentralControl.mcNO) +
				// System.getProperty("line.separator")
				// + "SUT " + PythagorasData. + System.getProperty("line.separator")
				+ "Result: continuously/discontinuously glue spilling" + System.getProperty("line.separator")
				+ "Glue Spill Rate: " + glueSpillRate();
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

	public static double glueSpillRate() throws IOException {
		Time.getTime();
		String path = System.getProperty("user.dir") + "/" + "AA" + "/" + Time.year + "/" + Time.month + "/" + Time.day
				+ ".xlsx";
		File xlsx = new File(path);
		int rowIndex = 0;
		int failureCount = 0;
		if (xlsx.exists()) {
			InputStream iStream;
			Workbook wb;

			
			iStream = new FileInputStream(xlsx);
			wb = new XSSFWorkbook(iStream);
			rowIndex = ExcelOperation.getEmptyRow(wb, 0, "A1");

			for (int i = 1; i < rowIndex; i++) {
				Sheet sheet = wb.getSheetAt(0);
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(7);
				if(cell.getStringCellValue().equals("NG")) {
					failureCount++;
				}
			}

		}
//		System.out.println(failureCount);
//		System.out.println(rowIndex);
		BigDecimal bd_rate = new BigDecimal((double)failureCount/(rowIndex-1));
		double rate = bd_rate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return rate;
	}

	public static void main(String[] args) throws IOException {
		writeEMail();
//		double i = glueSpillRate();
//		System.out.println(i);
	}
}
