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
		// ����excel������
		Workbook wb = new XSSFWorkbook();
		// ����sheet��ҳ��

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
		// ����һ���ļ� ����Ϊworkbooks.xlsx
		FileOutputStream fileOut = new FileOutputStream("d:\\workbooks.xlsx");
		// �����洴���Ĺ�����������ļ���
		wb.write(fileOut);
		// �ر������
		fileOut.close();
	}

	// ʹ��POI����excel�������ļ�
	public static void readWorkBook() throws Exception {
		// poi��ȡexcel
		// ����Ҫ������ļ���������
		InputStream inp = new FileInputStream("d:\\workbooks.xlsx");

		// �������������������� ��������������
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
		// �ر�������
		inp.close();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		Excel4J.createWorkBook();
		Excel4J.readWorkBook();

	}

}
