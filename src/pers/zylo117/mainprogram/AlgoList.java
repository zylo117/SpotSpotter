package pers.zylo117.mainprogram;

import java.io.IOException;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.fileprocessor.FileDetecter;
import pers.zylo117.fileprocessor.FileListener;
import pers.zylo117.patternrecognition.Binaryzation;
import pers.zylo117.patternrecognition.GetPixelArray;
import pers.zylo117.patternrecognition.ROI_Irregular;
import pers.zylo117.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.pictureprocess.Picture;
import pers.zylo117.pictureprocess.drawer.Draw;
import pers.zylo117.pictureprocess.drawer.DrawPoint;
import pers.zylo117.toolbox.Timer;
import pers.zylo117.toolbox.mathBox.AngleTransform;
import pers.zylo117.toolbox.mathBox.Line;
import pers.zylo117.toolbox.mathBox.Regression;
import pers.zylo117.viewer.CentralControl;
import pers.zylo117.viewer.MatView;

public class AlgoList {

	public static void godzilla() throws IOException {

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
							Parameter.threshold);
				}
				break;
			} else {
				System.out.println("File not exists, skipping");
				break;
			}
		}
	}

	public static void pythagoras_G() throws IOException {
		Timer.timer(10);

		String input = PathManagement.monitorPath + FileListener.filename;
		// String input =
		// "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/1.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output1.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println(input);
		Mat imgOrigin = Imgcodecs.imread(input);
		Picture pic = new Picture(imgOrigin);

		// 二值化获得初步ROI
		ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, 20);
		// 剔除边缘、角落等的精确ROI
		Mat roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP, pic.lrP, 2, 2, true,
				0.4, 0.4);
		// ROI按区域分级阀值
//		Mat roi_visiable = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP, pic.lrP, 11, 13, true,
//				0.15, 0.3);

		// 可选显示步骤图像
		 MatView.imshow(imgOrigin, "Original Image");
		 MatView.imshow(roi, "ROI");
//		 MatView.imshow(roi_visiable, "ROI_HL");

		// 标记并计数Spot
		Mat out = imgOrigin.clone();
		List<Point> spotList = SpotSpotter.spotList(roi, 0.15);
		Draw.pointList(out, spotList, 1, 1);
		MatView.imshow(out, "Output");

		// 标记回归直线
		// if(spotList.size()>3)
		Line line = Regression.line(spotList);
		Point startP = new Point(line.solveX(0), 0);
		Point endP = new Point(line.solveX(out.height() - 1), out.height() - 1);
		Draw.line_P2P(out, startP, endP);
		MatView.imshow(out, "lineOutput");
	}
}