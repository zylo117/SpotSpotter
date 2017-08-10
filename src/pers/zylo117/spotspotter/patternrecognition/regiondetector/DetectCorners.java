package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class DetectCorners {

	public static Point[] pCorners;
	public static Mat srcROI;

	public static Mat corners(Mat src, int roi_startX, int roi_startY, int width, int height, double density,
			boolean ifOutputRawPoint) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			final int maxCorners = 5, blockSize = 5;
			final double qualityLevel = 0.01, minDistance = density, k = 0.01;
			final boolean useHarrisDetector = true;
			MatOfPoint corners = new MatOfPoint();

			// …Ë÷√ROI
			Rect rect = new Rect(roi_startX, roi_startY, width, height);
			srcROI = new Mat(src, rect);
			
			if (srcROI.empty()) {
				throw new Exception("no file");
			}
			Mat gray = new Mat();

			Imgproc.cvtColor(srcROI, gray, Imgproc.COLOR_RGB2GRAY);
			Imgproc.goodFeaturesToTrack(gray, corners, maxCorners, qualityLevel, minDistance, new Mat(), blockSize,
					useHarrisDetector, k);
			pCorners = corners.toArray();

			if (ifOutputRawPoint) {
				for (int i = 0; i < pCorners.length; i++) {
					Imgproc.circle(srcROI, pCorners[i], 4, new Scalar(255, 255, 0), 2);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
		return src;
	}
}