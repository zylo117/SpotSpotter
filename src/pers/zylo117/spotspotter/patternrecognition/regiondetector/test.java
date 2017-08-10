package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.toolbox.GetMaxMin;

public class test {

	// Pythagoras-G
	// 推荐参数
	//

	// public static void Pythagoras_G(String input, String output) {
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/2.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output2.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread(input);

		// Upper-Left corner
		src = DetectCorners.corners(src, 206, 178, 20, 20, 15.0, false);

		// 求最优解
		double ulpoint = 0;
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			list.add(DetectCorners.pCorners[i].x);
		}
		ulpoint = GetMaxMin.getMax(list);
		// 求最大值的点的序号
		int pointNO;
		for(pointNO = 0; pointNO < DetectCorners.pCorners.length; pointNO++ ) {
			if (ulpoint == DetectCorners.pCorners[pointNO].x) {
				break;
			}
		}
		System.out.println(ulpoint);
		System.out.println(pointNO);
		Imgproc.circle(DetectCorners.srcROI, DetectCorners.pCorners[pointNO], 4, new Scalar(255, 255, 0), 2);

		// Upper-Right corner
		src = DetectCorners.corners(src, 306, 178, 20, 20, 1.0, false);
		// Lower-Left corner
		src = DetectCorners.corners(src, 206, 326, 20, 20, 15.0, false);
		// Lower-Right corner
		src = DetectCorners.corners(src, 306, 326, 20, 20, 1.0, false);

		Imgcodecs.imwrite(output, src);

	}
}
