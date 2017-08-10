package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import java.awt.Point;
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
	// �Ƽ�����

	// public static void Pythagoras_G(String input, String output) {
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/2.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output2.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread(input);

		// Upper-Left corner
		src = DetectCorners.corners(src, 206, 178, 20, 20, 1.0, false);

		// �����Ž�
		upperLeft_optimus();
		
		// Upper-Right corner
		src = DetectCorners.corners(src, 306, 178, 20, 20, 1.0, false);
		// Lower-Left corner
		src = DetectCorners.corners(src, 206, 326, 20, 20, 15.0, false);
		// Lower-Right corner
		src = DetectCorners.corners(src, 306, 326, 20, 20, 1.0, false);

		Imgcodecs.imwrite(output, src);

	}

	private static void upperLeft_optimus() {
		// �����Ž�
		double[] temp_x = new double[DetectCorners.pCorners.length];
		double[] temp_y = new double[DetectCorners.pCorners.length];
		for (int i = 0; i < DetectCorners.pCorners.length; i++) {
			temp_x[i] = DetectCorners.pCorners[i].x;
			temp_y[i] = DetectCorners.pCorners[i].y;
		}
		double ulpoint_x = GetMaxMin.getMinFromArray(temp_x);
		double ulpoint_y = GetMaxMin.getMinFromArray(temp_y);
		org.opencv.core.Point point = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		System.out.println(point.x + "," + point.y);
		Imgproc.circle(DetectCorners.srcROI, point, 4, new Scalar(255, 255, 0), 2);
	}

}
