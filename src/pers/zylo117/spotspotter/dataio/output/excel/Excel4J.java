package pers.zylo117.spotspotter.dataio.output.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel4J {
	public static void createWorkBook() throws IOException {
		// ����excel������
		final Workbook wb = new XSSFWorkbook();
		// ����sheet��ҳ��
		final Sheet sheet1 = wb.createSheet("Sheet_1");
		final Sheet sheet2 = wb.createSheet("Sheet_2");

		for (int i = 0; i < 20; i = i + 2) {
			final Row row1 = sheet1.createRow(i);
			final Row row2 = sheet2.createRow(i);

			for (int j = 0; j < 10; j++) {

				row1.createCell(j).setCellValue(j + "new");
				row2.createCell(j).setCellValue(j + "This is a string");

			}
		}
		// ����һ���ļ� ����Ϊworkbooks.xlsx
		final FileOutputStream fileOut = new FileOutputStream("d:\\workbooks.xlsx");
		// �����洴���Ĺ�����������ļ���
		wb.write(fileOut);
		// �ر������
		fileOut.close();
	}

	// ʹ��POI����excel�������ļ�
	public static void readWorkBook() throws Exception {
		// poi��ȡexcel
		// ����Ҫ������ļ���������
		final InputStream inp = new FileInputStream("d:\\workbooks.xlsx");

		// �������������������� ��������������
		final Workbook wb = WorkbookFactory.create(inp);

		for (final Sheet sheet : wb) {

			System.out.println(sheet.getSheetName());
			for (final Row row : sheet) {

				for (final Cell cell : row) {

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

	 private static final String EXCEL_XLS = "xls";  
	    private static final String EXCEL_XLSX = "xlsx";  
	  
	    public static void writeExcel(List<Map> dataList, int cloumnCount,String finalXlsxPath){  
	        OutputStream out = null;  
	        try {  
	            // ��ȡ������  
	            final int columnNumCount = cloumnCount;  
	            // ��ȡExcel�ĵ�  
	            final File finalXlsxFile = new File(finalXlsxPath);  
	            final Workbook workBook = getWorkbok(finalXlsxFile);  
	            // sheet ��Ӧһ������ҳ  
	            final Sheet sheet = workBook.getSheetAt(0);  
	            /** 
	             * ɾ��ԭ�����ݣ����������� 
	             */  
	            final int rowNumber = sheet.getLastRowNum();  // ��һ�д�0��ʼ��  
	            System.out.println("ԭʼ�������������������У�" + rowNumber);  
	            for (int i = 1; i <= rowNumber; i++) {  
	                final Row row = sheet.getRow(i);  
	                sheet.removeRow(row);  
	            }  
	            // �����ļ��������������ӱ����������У���������sheet�������κβ�����������Ч  
	            out =  new FileOutputStream(finalXlsxPath);  
	            workBook.write(out);  
	            /** 
	             * ��Excel��д������ 
	             */  
	            for (int j = 0; j < dataList.size(); j++) {  
	                // ����һ�У��ӵڶ��п�ʼ������������  
	                final Row row = sheet.createRow(j + 1);  
	                // �õ�Ҫ�����ÿһ����¼  
	                final Map dataMap = dataList.get(j);  
	                final String name = dataMap.get("BankName").toString();  
	                final String address = dataMap.get("Addr").toString();  
	                final String phone = dataMap.get("Phone").toString();  
	                for (int k = 0; k <= columnNumCount; k++) {  
	                // ��һ����ѭ��  
	                final Cell first = row.createCell(0);  
	                first.setCellValue(name);  
	          
	                final Cell second = row.createCell(1);  
	                second.setCellValue(address);  
	          
	                final Cell third = row.createCell(2);  
	                third.setCellValue(phone);  
	                }  
	            }  
	            // �����ļ��������׼��������ӱ����������У���������sheet�������κβ�����������Ч  
	            out =  new FileOutputStream(finalXlsxPath);  
	            workBook.write(out);  
	        } catch (final Exception e) {  
	            e.printStackTrace();  
	        } finally{  
	            try {  
	                if(out != null){  
	                    out.flush();  
	                    out.close();  
	                }  
	            } catch (final IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        System.out.println("���ݵ����ɹ�");  
	    }  
	  
	    /** 
	     * �ж�Excel�İ汾,��ȡWorkbook 
	     * @param in 
	     * @param filename 
	     * @return 
	     * @throws IOException 
	     */  
	    public static Workbook getWorkbok(File file) throws IOException{  
	        Workbook wb = null;  
	        final FileInputStream in = new FileInputStream(file);  
	        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
	            wb = new HSSFWorkbook(in);  
	        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
	            wb = new XSSFWorkbook(in);  
	        }  
	        return wb;  
	    }  
}
