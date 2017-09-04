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

	 private static final String EXCEL_XLS = "xls";  
	    private static final String EXCEL_XLSX = "xlsx";  
	  
	    public static void writeExcel(List<Map> dataList, int cloumnCount,String finalXlsxPath){  
	        OutputStream out = null;  
	        try {  
	            // 获取总列数  
	            int columnNumCount = cloumnCount;  
	            // 读取Excel文档  
	            File finalXlsxFile = new File(finalXlsxPath);  
	            Workbook workBook = getWorkbok(finalXlsxFile);  
	            // sheet 对应一个工作页  
	            Sheet sheet = workBook.getSheetAt(0);  
	            /** 
	             * 删除原有数据，除了属性列 
	             */  
	            int rowNumber = sheet.getLastRowNum();  // 第一行从0开始算  
	            System.out.println("原始数据总行数，除属性列：" + rowNumber);  
	            for (int i = 1; i <= rowNumber; i++) {  
	                Row row = sheet.getRow(i);  
	                sheet.removeRow(row);  
	            }  
	            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
	            out =  new FileOutputStream(finalXlsxPath);  
	            workBook.write(out);  
	            /** 
	             * 往Excel中写新数据 
	             */  
	            for (int j = 0; j < dataList.size(); j++) {  
	                // 创建一行：从第二行开始，跳过属性列  
	                Row row = sheet.createRow(j + 1);  
	                // 得到要插入的每一条记录  
	                Map dataMap = dataList.get(j);  
	                String name = dataMap.get("BankName").toString();  
	                String address = dataMap.get("Addr").toString();  
	                String phone = dataMap.get("Phone").toString();  
	                for (int k = 0; k <= columnNumCount; k++) {  
	                // 在一行内循环  
	                Cell first = row.createCell(0);  
	                first.setCellValue(name);  
	          
	                Cell second = row.createCell(1);  
	                second.setCellValue(address);  
	          
	                Cell third = row.createCell(2);  
	                third.setCellValue(phone);  
	                }  
	            }  
	            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效  
	            out =  new FileOutputStream(finalXlsxPath);  
	            workBook.write(out);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally{  
	            try {  
	                if(out != null){  
	                    out.flush();  
	                    out.close();  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        System.out.println("数据导出成功");  
	    }  
	  
	    /** 
	     * 判断Excel的版本,获取Workbook 
	     * @param in 
	     * @param filename 
	     * @return 
	     * @throws IOException 
	     */  
	    public static Workbook getWorkbok(File file) throws IOException{  
	        Workbook wb = null;  
	        FileInputStream in = new FileInputStream(file);  
	        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
	            wb = new HSSFWorkbook(in);  
	        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
	            wb = new XSSFWorkbook(in);  
	        }  
	        return wb;  
	    }  
}
