package pers.zylo117.spotspotter.dataio.output.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pers.zylo117.spotspotter.dataio.input.project.GA_AA_Data;
import pers.zylo117.spotspotter.dataio.output.excel.ExcelOperation;
import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.toolbox.Time;

public class EMailContent {
	public static void write(){
		String currrentPath = System.getProperty("user.dir") + "/" + "email_content.txt";

		File counterTXT = new File(currrentPath);

		if (!counterTXT.exists()) {
			try {
				FileOutputStream fileOut = new FileOutputStream(counterTXT);
			} catch (FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		Time.getTime();
		String str = "Date " + Time.datetime_slash + System.getProperty("line.separator") + "Category "
				+ CentralControl.productN + System.getProperty("line.separator")
				// + "AA " + Integer.toString(CentralControl.mcNO) +
				// System.getProperty("line.separator")
				// + "SUT " + PythagorasData. + System.getProperty("line.separator")
				+ "Daily Failure Rate Summary" + System.getProperty("line.separator")
//				+ "AA Glue Spill Rate: " + failureRate("AA") + "%" + System.getProperty("line.separator")
				+ "GA Dust Detect Rate: " + failureRate("GA") + "%";
		
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

	public static double failureRate(String processName) {
		Time.getTime();
		String path = System.getProperty("user.dir") + "/" + processName + "/" + Time.year + "/" + Time.month + "/"
				+ Time.day + ".xlsx";
		File xlsx = new File(path);
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
					Sheet sheet = wb.getSheetAt(0);
					Row row = sheet.getRow(i);
					Cell cell = row.getCell(10);
					if (cell.getStringCellValue().equals("NG")) {
						failureCount++;
					}
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		// System.out.println(failureCount);
		// System.out.println(rowIndex);
		BigDecimal bd_rate = new BigDecimal((double) failureCount * 100 / (rowIndex - 1));
		double rate = bd_rate.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
		return rate;
	}

	public static void main(String[] args) throws IOException {
		write();
		// double i = glueSpillRate();
		// System.out.println(i);
	}
}
