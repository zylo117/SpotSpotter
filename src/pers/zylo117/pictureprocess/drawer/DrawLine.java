package pers.zylo117.pictureprocess.drawer;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DrawLine {

	// 点到点画线
	public static Mat lineP2P(Mat inputmat, Point startP, Point endP) {
		Imgproc.line(inputmat, startP, endP, new Scalar(255, 255, 0), 1);
		return inputmat;
	}
}
