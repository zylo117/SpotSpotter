package pers.zylo117.spotspotter.mainprogram;

import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.SpotSpotter;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.pictureprocess.drawer.DrawPoint;
import pers.zylo117.spotspotter.viewer.MatView;

public class Main {

	// 图片IO路径去PathManagement。java修改
	// 识别参数请去ParameterManagement。java修改

	public static void main(String[] args) throws Exception {
//		Scanner inputKey = new Scanner(System.in);
//
//		// 询问算法
//		System.out.println("Welcome running Classified Project Argus");
//		System.out.println("Press Enter to Go Default");
//		String answer = inputKey.nextLine();
//
//		if (answer.equals("1") || answer.equals("1")) {
//		}
//
//		PathManagement.definePath();
//		FileListener.Autoscript(2);
//
//		System.exit(0);

		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/2.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output2.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat imgOrigin = Imgcodecs.imread(input);
		Picture pic = new Picture(imgOrigin);
		ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, 20);

		Mat roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP, pic.lrP, 2, true,
				0.1, 0.3);
		MatView.imshow(imgOrigin, "Original Image");
		MatView.imshow(roi, "ROI");

		Mat out = imgOrigin.clone();
		List<Point> spotList = SpotSpotter.spotList(roi, 0.1);
		DrawPoint.pointList(out, spotList);
		MatView.imshow(out, "Output");
	}

}
