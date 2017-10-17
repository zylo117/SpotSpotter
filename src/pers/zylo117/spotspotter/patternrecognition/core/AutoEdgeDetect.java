package pers.zylo117.spotspotter.patternrecognition.core;

import java.awt.font.NumericShaper.Range;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.gui.viewer.BImgView;
import pers.zylo117.spotspotter.gui.viewer.MatView;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.SetPixelArray;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.mathBox.Line;

public class AutoEdgeDetect {
	// Default: sigma = 0.33
	public static Mat autoCanny(Mat image, double sigma, boolean ifBlur, double inwardCropRatio) {
		// MatView.imshow(image, "MAT");
		// Mat pyS = new Mat();
		// Imgproc.pyrMeanShiftFiltering(image, pyS, 21, 51);
		// MatView.imshow(pyS, "py1");

		Rect rect = new Rect((int) (image.width() * inwardCropRatio / 2), (int) (image.height() * inwardCropRatio / 2),
				(int) (image.width() * (1 - inwardCropRatio)), (int) (image.height() * (1 - inwardCropRatio)));

		Mat sub = new Mat(image, rect);

		byte[] array = GetPixelArray.getRaster(sub);
		// for (int i = 0; i < image.width() * image.height(); i++) {
		// if (i == 25)
		// System.out.println(array[i]);
		// }

		List<Integer> intRaseter = new ArrayList<>();
		for (int i = 1; i < array.length; i++) {
			if (array[i] < 0)
				intRaseter.add(array[i] + 256);
			else
				intRaseter.add((int) array[i]);

		}
		// for (int i = 0; i < image.width() * image.height(); i++) {
		// if (i == 25)
		// System.out.println(intRaseter.get(i));
		// }
		// BufferedImage bImage = SetPixelArray.fromRaster(array, image.width(),
		// image.height());
		// BImgView.imshow(bImage, "BI");

		int median = 0;
		Collections.sort(intRaseter);
		int length = intRaseter.size();
		if (length % 2 == 0)
			median = (intRaseter.get(intRaseter.size() / 2 - 1) + intRaseter.get(intRaseter.size() / 2)) / 2;
		else
			median = intRaseter.get(intRaseter.size() / 2);

		int lower = (int) (0 < ((1.0 - sigma) * median) ? ((1.0 - sigma) * median) : 0);
		int upper = (int) (255 > ((1.0 + sigma) * median) ? ((1.0 + sigma) * median) : 255);

		Mat blurred = new Mat();
		Mat edge = Mat.zeros(image.size(), CvType.CV_8UC1);
		if (ifBlur) {
			Imgproc.GaussianBlur(image, blurred, new Size(11, 11), 0);
			Imgproc.Canny(blurred, edge, lower, upper);
		} else
			Imgproc.Canny(image, edge, lower, upper);

		System.out.println(median);
		System.out.println(lower);
		System.out.println(upper);
		MatView.imshow(edge, "Edge");

		return edge;
	}

	public static Mat largestContour(Mat input) {
		List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
		Imgproc.findContours(input.clone(), cnts, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		// MatView.imshow(input, "Img");
		System.out.println(cnts.size());
		System.out.println(cnts);
		double maxArea = Imgproc.contourArea(cnts.get(0));
		int maxIdx = 0;
		for (int i = 0; i < cnts.size(); i++) {
			if (Imgproc.contourArea(cnts.get(i)) > maxArea) {
				maxArea = Imgproc.contourArea(cnts.get(i));
				maxIdx = i;
			}
			// System.out.println(Imgproc.contourArea(cnts.get(i)));
		}
		// System.out.println("maxArea " + maxArea);
		// System.out.println("maxIdx " + maxIdx);

		// for (int i = 0; i < cnts.size(); i++) {
		// Imgproc.drawContours(input, cnts, i, new Scalar((int) (Math.random() * 256),
		// (int) (Math.random() * 256),
		// (int) (Math.random() * 256)), 4);
		// }

		// // 画出最大的区域
		Mat zero = Mat.zeros(input.size(), CvType.CV_8UC3);
		Imgproc.drawContours(zero, cnts, maxIdx, new Scalar(255, 255, 255), -1);
		//
		// MatView.imshow(zero, "Contour");
		Imgproc.cvtColor(zero, cnts.get(maxIdx), CvType.CV_8UC3);
		return zero;
	}

	public static void main(String[] args) {
		System.loadLibrary("opencv_java330_64");
		String input = "D:\\tmp\\8.jpg";
		Mat image = Imgcodecs.imread(input, 1);
		MatView.imshow(image, "Ori");
		
		Mat bin = new Mat();
		Imgproc.cvtColor(image, bin, Imgproc.COLOR_RGB2GRAY);
		Imgproc.threshold(bin, bin, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY_INV);
		MatView.imshow(bin, "Bin");
		
		double percentOfCrop = 0.55;

		new Mat();
		Rect rect = new Rect((int) (bin.width() * percentOfCrop / 2), (int) (bin.height() * percentOfCrop / 2),
				(int) (bin.width() * (1 - percentOfCrop)), (int) (bin.height() * (1 - percentOfCrop)));

		Mat roi = ROI_Irregular.RectangleSubROI(bin, rect);

		MatView.imshow(roi, "ROI");
//		roi = autoCanny(roi, 0.67, true, percentOfCrop);
//		MatView.imshow(roi, "Edge");
		roi = largestContour(roi);
		MatView.imshow(roi, "Contour");
		
		Core.bitwise_and(image, roi, roi);
		MatView.imshow(roi, "Final ROI");
	}

}
