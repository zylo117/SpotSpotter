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
import pers.zylo117.spotspotter.dataio.input.project.GA_AA_Data;
import pers.zylo117.spotspotter.dataio.output.excel.ExcelOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.toolbox.Time;

public class EMailContent {
	public static void write() {
		final String currrentPath = System.getProperty("user.dir") + "/" + "email_content.txt";

		final File counterTXT = new File(currrentPath);

		if (!counterTXT.exists()) {
			try {
				final FileOutputStream fileOut = new FileOutputStream(counterTXT);
			} catch (final FileNotFoundException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}

		Time.getTime();
		final StringBuilder str = new StringBuilder("Date ");
		str.append(Time.datetime_slash).append(System.getProperty("line.separator")).append("Category ")
				.append(CentralControl.productN).append(System.getProperty("line.separator"))
				.append("Daily Failure Rate Summary").append(System.getProperty("line.separator"))
				.append("GA Dust Detect Rate: ").append(failureRate()).append("%");

		PrintWriter pfp;
		try {
			pfp = new PrintWriter(counterTXT);
			pfp.print(str);
			pfp.close();
		} catch (final FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public static double failureRate() {
		// Time.getTime();
		// final String path = System.getProperty("user.dir") + "/" + processName + "/"
		// + Time.year + "/" + Time.month
		// + "/" + Time.day + ".xlsx";
		// final File xlsx = new File(path);
		int rowIndex = 0;
		int failureCount = 0;
		final Workbook wb = GA_AA_Data.tmpWB;

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
		write();
		// double i = glueSpillRate();
		// System.out.println(i);
	}
}
