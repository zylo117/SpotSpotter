package pers.zylo117.spotspotter.dataoutput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel4J {
	public static void createWorkBook() throws IOException {
		// 创建excel工作簿
		Workbook wb = new XSSFWorkbook();
		// 创建sheet（页）

		Sheet sheet1 = wb.createSheet("Sheet_1");
		Sheet sheet2 = wb.createSheet("Sheet_2");

		for (int i = 0; i < 20; i = i + 2) {
			Row row1 = sheet1.createRow(i);
			Row row2 = sheet2.createRow(i);

			for (int j = 0; j < 10; j++) {

				row1.createCell(j).setCellValue(j + "new");
				row2.createCell(j).setCellValue(j + "This is a string");

			}
		}
		// 创建一个文件 命名为workbooks.xlsx
		FileOutputStream fileOut = new FileOutputStream("d:\\workbooks.xlsx");
		// 把上面创建的工作簿输出到文件中
		wb.write(fileOut);
		// 关闭输出流
		fileOut.close();
	}

	// 使用POI读入excel工作簿文件
	public static void readWorkBook() throws Exception {
		// poi读取excel
		// 创建要读入的文件的输入流
		InputStream inp = new FileInputStream("d:\\workbooks.xlsx");

		// 根据上述创建的输入流 创建工作簿对象
		Workbook wb = WorkbookFactory.create(inp);

		for (Sheet sheet : wb) {

			System.out.println(sheet.getSheetName());
			for (Row row : sheet) {

				for (Cell cell : row) {

					System.out.print(cell.toString() + "  ");
				}

				System.out.println();
			}
		}
		// 关闭输入流
		inp.close();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Excel4J.createWorkBook();
		Excel4J.readWorkBook();

	}

}
