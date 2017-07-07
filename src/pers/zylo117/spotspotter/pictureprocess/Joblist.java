package pers.zylo117.spotspotter.pictureprocess;

import java.io.IOException;
import java.io.File;

import pers.zylo117.spotspotter.fileprocessor.FileDetecter;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.fileprocessor.FileNameTrim;
import pers.zylo117.spotspotter.fileprocessor.Obj2String;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.ImageStream2File;

public class Joblist {

	public static void joblist() throws IOException {
		
		// 判断文件是否是否写入InputStream
		if (null == FileListener.filename || "".equals(FileListener.filename)) {
			System.out.println("Input is null");
		}

		String inputimage = PicProcess.inputdir + FileListener.filename;
		String rawoutputimage = PicProcess.rawoutputdir + FileListener.filename;
		String finaloutputimage = PicProcess.finaloutputdir + FileListener.filename;

		// 提取像素矩阵前对文件是否存在进行判断
		while (true) {
			if (FileDetecter.judgeFileExists(inputimage)) {
				// 提取像素矩阵,getData(文件,缓冲延迟时间（机器越强，文件越小，延迟越小）,识别起始点x,识别起始点y,识别长度,识别宽度)
				GetPixelArray.getData(inputimage, rawoutputimage, 1, 384, 338, 570, 340);
				inputimage = null;
				break;
			} else
				System.out.println("File not exists, skipping");
		}
	}

}
