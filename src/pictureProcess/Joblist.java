package pictureProcess;

import java.io.IOException;
import java.io.File;

import fileListener.FileListener;
import fileListener.Filedetecter;
import fileListener.FileNameTrim;
import fileListener.obj2String;
import patternRecognition.ColorValue;
import patternRecognition.test;
import patternRecognition.test;

public class Joblist {

	public static void joblist() throws IOException {

		if (null==FileListener.filename||"".equals(FileListener.filename)) {
			System.out.println("Input is null");
		}
		
		String wipname = PicProcess.original + FileListener.filename;
		String IRCFname = PicProcess.IRCF + FileListener.filename;
		String ROIname = PicProcess.ROI + FileListener.filename;
		
		// 图形分割
		//PicProcess.crop2(wipname, ROIname, "jpg", 82, 86, 570, 340);
		// ROInaive.spitImage(obj2String.o2s(ROIname));
		
		//System.out.println(wipname);
		// 提取像素矩阵
		while(true)
			if(Filedetecter.judgeFileExists(wipname)) {
				try{  
					Thread.currentThread();
					Thread.sleep(500);//毫秒   
				}   
				catch(Exception e){
					e.printStackTrace();
				} 
				ColorValue.getData(wipname, 384, 338, 570, 340);
				wipname = null;
				break;
			}
		//String a = "D:\\test\\original\\20170530_160408715_ALL_T722G58GR.01_M441F31706-32_Pkg32_Chip.jpg";
		//String b = "D:\\test\\original\\20170530_160410093_ALL_T722G58GR.01_M441F31706-33_Pkg33_Glass.jpg";		
		//ColorValue.getData(a, 384, 338, 570, 340);
		//ColorValue.getData(b, 384, 338, 570, 340);
		//test.testcolor(wipname);
		System.out.println("Spitted");
		System.out.println("");

		// 文件名提取,分类
		// 状态
		// System.out.println(FileNameTrim.getStatus());
		// 年份
		// System.out.println(FileNameTrim.getYear());

	}

}
