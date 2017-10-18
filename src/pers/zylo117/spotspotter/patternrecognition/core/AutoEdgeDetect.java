package pers.zylo117.spotspotter.patternrecognition.core;

import java.awt.font.NumericShaper.Range;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
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
import pers.zylo117.spotspotter.toolbox.MatOfPointCovert;
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

	public static Mat[] largestContour(Mat raw, Mat crop, double minArea, int offset) {
		//0为ROI，1为标示框
		Mat[] matSet = new Mat[3];
		
		List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
		Imgproc.findContours(crop.clone(), cnts, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		// MatView.imshow(input, "Img");
//		System.out.println(cnts.size());
//		System.out.println(cnts);
		// double maxArea = Imgproc.contourArea(cnts.get(0));
		// int maxIdx = 0;
		// for (int i = 0; i < cnts.size(); i++) {
		// if (Imgproc.contourArea(cnts.get(i)) > maxArea) {
		// maxArea = Imgproc.contourArea(cnts.get(i));
		// maxIdx = i;
		// }
		//// System.out.println(Imgproc.contourArea(cnts.get(i)));
		// }

		Map<Integer, Double> map = new HashMap<>();
		for (int i = 0; i < cnts.size(); i++) {
			map.put(i, Imgproc.contourArea(cnts.get(i)));
		}

		List<Map.Entry<Integer, Double>> cnts_AreaOrder = new ArrayList<Map.Entry<Integer, Double>>(map.entrySet());
		Collections.sort(cnts_AreaOrder, new Comparator<Map.Entry<Integer, Double>>() {
			// 降序排序
			@Override
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
				// return o1.getValue().compareTo(o2.getValue());
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		// System.out.println(cnts_AreaOrder);
//		for (int i = 0; i < cnts_AreaOrder.size(); i++) {
//			System.out.println(cnts_AreaOrder.get(i).getKey() + ":" + cnts_AreaOrder.get(i).getValue());
//		}

		// System.out.println("maxArea " + maxArea);
		// System.out.println("maxIdx " + maxIdx);

		// for (int i = 0; i < cnts.size(); i++) {
		// Imgproc.drawContours(input, cnts, i, new Scalar((int) (Math.random() * 256),
		// (int) (Math.random() * 256),
		// (int) (Math.random() * 256)), 4);
		// }

		// 求最适合的Contour
		int minIdx = 0;
		for (int i = 1; i < cnts_AreaOrder.size(); i++) {
			if (cnts_AreaOrder.get(i).getValue() < minArea) {
				minIdx = cnts_AreaOrder.get(i - 1).getKey();
				break;
			}
		}

//		System.out.println(minIdx);

		// // 画出最大的区域
		Mat roi = Mat.zeros(crop.size(), CvType.CV_8UC3);
		Imgproc.drawContours(roi, cnts, minIdx, new Scalar(255, 255, 255), -1);
		
//		// 转换MatOfPoint为MatOfPoint2f
//		MatOfPoint2f mop2f = MatOfPointCovert.toMOP2F(cnts.get(minIdx));
//		// 输出最小外接矩形
//		RotatedRect roRect = Imgproc.minAreaRect(mop2f);
//		Point[] vertices = new Point[4];
//		roRect.points(vertices);
//		roi = ROI_Irregular.irregularQuadrangle_Simplified(raw, vertices[1], vertices[2], vertices[0], vertices[3], 0, 0, false, 0, 0);
//		MatView.imshow(roi, "Rect");
		
		Imgproc.drawContours(raw, cnts, minIdx, new Scalar(200, 128, 128), offset * 2);
		
		Mat zero = Mat.zeros(crop.size(), CvType.CV_8UC3);
		Imgproc.drawContours(zero, cnts, minIdx, new Scalar(255, 255, 255), offset * 2);	
		
		matSet[0] = roi;
		matSet[1] = raw;	
		matSet[2] = zero;
		
		return matSet;
	}

	public static Mat[] iRCF_NH_ME(Mat image, int blurVal, double minAreaSize, int offset){
		Mat[] matSet = new Mat[3];
		
		//		MatView.imshow(image, "Ori");

		Mat gray = new Mat();
		Imgproc.cvtColor(image, gray, Imgproc.COLOR_RGB2GRAY);

		double percentOfCrop = 0.5;
		Rect rect = new Rect((int) (gray.width() * percentOfCrop / 2), (int) (gray.height() * percentOfCrop / 2),
				(int) (gray.width() * (1 - percentOfCrop)), (int) (gray.height() * (1 - percentOfCrop)));

		Mat findThresh = gray.clone().submat(rect);
		double thresh = Imgproc.threshold(findThresh, findThresh, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY);

		Mat roi = ROI_Irregular.RectangleSubROI(gray, rect);
		
		Imgproc.GaussianBlur(roi, roi, new Size(blurVal, blurVal), 0);
		
//		MatView.imshow(roi, "ROI");

		Imgproc.threshold(roi, roi, thresh, 255, Imgproc.THRESH_BINARY);
//		MatView.imshow(roi, "Bin");

		matSet = largestContour(image.clone(), roi, minAreaSize, offset);
		
		Mat emptyBox = matSet[2];
//		MatView.imshow(emptyBox, "EmptyBox");
		
		roi = matSet[0];
//		MatView.imshow(roi, "Contour");

		Core.subtract(roi, emptyBox, roi);
		
//		Core.bitwise_xor(roi, emptyBox, roi);
//		MatView.imshow(roi, "Contour2");
		
		Mat box = matSet[1];
//		MatView.imshow(box, "Box");
		
		Core.bitwise_and(image, roi, roi);
//		MatView.imshow(roi, "Final ROI1");
		
		return matSet;
	}
	
	public static void main(String[] args) {
		System.loadLibrary("opencv_java330_64");
		String input = "D:\\tmp\\9.jpg";
		Mat image = Imgcodecs.imread(input, 1);
//		MatView.imshow(image, "Ori");
		iRCF_NH_ME(image, 21, 200000, 16);
	}

}
