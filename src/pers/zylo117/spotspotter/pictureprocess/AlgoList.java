package pers.zylo117.spotspotter.pictureprocess;

import java.io.IOException;
import pers.zylo117.spotspotter.fileprocessor.FileDetecter;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.Binaryzation;
import pers.zylo117.spotspotter.patternrecognition.SpotSpotter;
import pers.zylo117.spotspotter.pictureprocess.TargetClassifier;

public class AlgoList {

	public static void markingAlgo() throws IOException {

		// 判断文件是否是否写入InputStream
		if (null == FileListener.filename || "".equals(FileListener.filename)) {
			System.out.println("Input is null");
		}

		String inputimage = PathManagement.inputdir + FileListener.filename;
		String rawoutputimage = PathManagement.rawoutputdir + FileListener.filename;
		String bipicoutputimage = PathManagement.bipicdir + FileListener.filename;
		String finaloutputimage = PathManagement.finaloutputdir + FileListener.filename;

		Parameter.getParameter(inputimage);
		System.out.println(TargetClassifier.getClass("Process Name" + inputimage));

		// 第一项
		// 提取像素矩阵前对文件是否存在进行判断
		while (!TargetClassifier.getClass(inputimage).equals("Unknown")) {
			if (FileDetecter.judgeFileExists(inputimage)) {
				// 提取像素矩阵,getData(文件,缓冲延迟时间（机器越强，文件越小，延迟越小）,识别起始点x,识别起始点y,识别长度,识别宽度)
				GetPixelArray.getData(inputimage, 10, Parameter.ROIstart_x, Parameter.ROIstart_y, Parameter.ROIWidth,
						Parameter.ROIHeight);
				inputimage = null;

				if (GetPixelArray.ready2spot == 1) {
					SpotSpotter.marking(finaloutputimage, Parameter.ROIWidth, Parameter.ROIHeight, 1,
							Parameter.theshold);
				}
				break;
			} else {
				System.out.println("File not exists, skipping");
				break;
			}
		}
	}

}