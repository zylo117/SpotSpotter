package pers.zylo117.spotspotter.pictureprocess.drawer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Draw {

	// �㵽�㻭��
	public static void line_P2P(Mat inputmat, Point startP, Point endP) {
		Imgproc.line(inputmat, startP, endP, new Scalar(255, 255, 0), 1);
	}

	public static void point(Mat input, Point p, int radius, int size) {
		Imgproc.circle(input, p, radius, new Scalar(255, 0, 255), size);
	}

	public static void pointList(Mat input, List<Point> list, int radius, int size) {
		for (int i = 0; i < list.size(); i++) {
			Point p = list.get(i);
			Imgproc.circle(input, p, radius, new Scalar(255, 0, 255), size);
		}
		System.out.println("Done Drawing");
	}

	// ratio should be over 10
	public static void pointMapList(Mat input, List<Map<Point, Double>> list, int radiusRatio, int size) {
		for (int i = 0; i < list.size(); i++) {
			Map dataMap = new HashMap<>();
			dataMap = list.get(i);

			Set<Map.Entry<Point, Double>> entryseSet = dataMap.entrySet();
			for (Map.Entry<Point, Double> entry : entryseSet) {
				Point p = entry.getKey();
				int radius = (new Double(radiusRatio * entry.getValue())).intValue();
				Imgproc.circle(input, p, radius, new Scalar(255, 0, 255), size);
			}
		}
		System.out.println("Done Drawing");
	}
}
