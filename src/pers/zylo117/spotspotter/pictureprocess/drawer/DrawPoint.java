package pers.zylo117.spotspotter.pictureprocess.drawer;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DrawPoint {
	public static void point(Mat input, Point p) {
		Imgproc.circle(input, p, 0, new Scalar(255, 0, 255), 0);
	}
	
	public static void pointList(Mat input, List<Point> list) {
		for(int i = 1;i<list.size();i++){
			Point p = list.get(i);
			Imgproc.circle(input, p, 0, new Scalar(255, 0, 255), 0);
		}
	}
}
