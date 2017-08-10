package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;

public class test {

	// Pythagoras-G
	// 推荐参数

	// public static void Pythagoras_G(String input, String output) {
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/2.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output2.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread(input);

		// Upper-Left corner
		src = DetectCorners.corners(src, 206, 182, 20, 20, 10, false);
		// 求最优解
		upperLeft_optimus();
		
		// Upper-Right corner
		src = DetectCorners.corners(src, 302, 182, 20, 20, 15, false);
		// 求最优解
		upperRight_optimus();
		
		// Lower-Left corner
		src = DetectCorners.corners(src, 206, 326, 20, 20, 15, false);
		// 求最优解
		lowerLeft_optimus();
		
		// Lower-Right corner
		src = DetectCorners.corners(src, 302, 326, 20, 20, 0.5, false);
		// 求最优解
		lowerRight_optimus();
		
		Imgcodecs.imwrite(output, src);

	}

	private static void upperLeft_optimus() {
		// 求最优解
		double[] temp_x = new double[DetectCorners.pCorners.length];
		double[] temp_y = new double[DetectCorners.pCorners.length];
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			temp_x[i] = DetectCorners.pCorners[i].x;
			temp_y[i] = DetectCorners.pCorners[i].y;
		}
		double ulpoint_x = GetMaxMinMidAvg.getMinFromArray(temp_x);
		double ulpoint_y = GetMaxMinMidAvg.getMidFromArray(temp_y);
		org.opencv.core.Point point = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		System.out.println(point.x + "," + point.y);
		Imgproc.circle(DetectCorners.srcROI, point, 4, new Scalar(255, 255, 0), 2);
	}

	private static void upperRight_optimus() {
		// 求最优解
		double[] temp_x = new double[DetectCorners.pCorners.length];
		double[] temp_y = new double[DetectCorners.pCorners.length];
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			temp_x[i] = DetectCorners.pCorners[i].x;
			temp_y[i] = DetectCorners.pCorners[i].y;
		}
		double ulpoint_x = GetMaxMinMidAvg.getMidFromArray(temp_x);
		double ulpoint_y = GetMaxMinMidAvg.getMinFromArray(temp_y);
		org.opencv.core.Point point = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		System.out.println(point.x + "," + point.y);
		Imgproc.circle(DetectCorners.srcROI, point, 4, new Scalar(255, 255, 0), 2);
	}
	
	private static void lowerLeft_optimus() {
		// 求最优解
		double[] temp_x = new double[DetectCorners.pCorners.length];
		double[] temp_y = new double[DetectCorners.pCorners.length];
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			temp_x[i] = DetectCorners.pCorners[i].x;
			temp_y[i] = DetectCorners.pCorners[i].y;
		}
		double ulpoint_x = GetMaxMinMidAvg.getMinFromArray(temp_x);
		double ulpoint_y = GetMaxMinMidAvg.getMinFromArray(temp_y);
		org.opencv.core.Point point = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		System.out.println(point.x + "," + point.y);
		Imgproc.circle(DetectCorners.srcROI, point, 4, new Scalar(255, 255, 0), 2);
	}
	
	private static void lowerRight_optimus() {
		// 求最优解
		double[] temp_x = new double[DetectCorners.pCorners.length];
		double[] temp_y = new double[DetectCorners.pCorners.length];
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			temp_x[i] = DetectCorners.pCorners[i].x;
			temp_y[i] = DetectCorners.pCorners[i].y;
		}
		double ulpoint_x = GetMaxMinMidAvg.getAvgFromArray(temp_x);
		double ulpoint_y = GetMaxMinMidAvg.getMidFromArray(temp_y);
		org.opencv.core.Point point = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		System.out.println(point.x + "," + point.y);
		Imgproc.circle(DetectCorners.srcROI, point, 4, new Scalar(255, 255, 0), 2);
	}
	
}
