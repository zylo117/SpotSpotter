package pers.zylo117.spotspotter.pictureprocess;

import java.io.IOException;
import java.io.File;

import pers.zylo117.spotspotter.fileprocessor.FileDetecter;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.fileprocessor.FileNameTrim;
import pers.zylo117.spotspotter.fileprocessor.Obj2String;
import pers.zylo117.spotspotter.patternrecognition.GetColorValue;

public class Joblist {

	public static void joblist() throws IOException {

		if (null == FileListener.filename || "".equals(FileListener.filename)) {
			System.out.println("Input is null");
		}

		String wipname = PicProcess.original + FileListener.filename;
		String IRCFname = PicProcess.IRCF + FileListener.filename;
		String ROIname = PicProcess.ROI + FileListener.filename;

		// 图形分割
		// PicProcess.crop2(wipname, ROIname, "jpg", 82, 86, 570, 340);
		// ROInaive.spitImage(obj2String.o2s(ROIname));
		// System.out.println(wipname);
		
		// 提取像素矩阵,getData(文件,延迟时间（机器越强，文件越小，延迟越小）,识别起始点x,识别起始点y,识别长度,识别宽度) 
		while (true) {
			if (FileDetecter.judgeFileExists(wipname)) {
				GetColorValue.getData(wipname, 500, 384, 338, 570, 340);
				wipname = null;
				break;
			}
			else System.out.println("File not exists, skipping");
		}
		System.out.println("Spitted");
		System.out.println("");
		// 文件名提取,分类
		// 状态
		// System.out.println(FileNameTrim.getStatus());
		// 年份
		// System.out.println(FileNameTrim.getYear());

	}

}
