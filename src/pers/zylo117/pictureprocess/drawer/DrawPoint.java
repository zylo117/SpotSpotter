package pers.zylo117.pictureprocess.drawer;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DrawPoint {
	public static void point(Mat input, Point p, int radius, int size) {
		Imgproc.circle(input, p, radius, new Scalar(255, 0, 255), size);
	}
	
	public static void pointList(Mat input, List<Point> list, int radius, int size) {
		for(int i = 0;i<list.size();i++){
			Point p = list.get(i);
			Imgproc.circle(input, p, radius, new Scalar(255, 0, 255), size);
		}
		System.out.println("Done Drawing");
	}
}
