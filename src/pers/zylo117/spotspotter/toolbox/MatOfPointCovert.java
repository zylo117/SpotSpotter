package pers.zylo117.spotspotter.toolbox;

import org.opencv.core.CvType;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;

public class MatOfPointCovert {
	public static MatOfPoint2f toMOP2F(MatOfPoint mop) {
		final MatOfPoint2f dst = new MatOfPoint2f();
		mop.convertTo(dst, CvType.CV_32F);
		return dst;
	}

	public static MatOfPoint toMOP(MatOfPoint2f mop2f) {
		final MatOfPoint dst = new MatOfPoint();
		mop2f.convertTo(dst, CvType.CV_32S);
		return dst;
	}
}
