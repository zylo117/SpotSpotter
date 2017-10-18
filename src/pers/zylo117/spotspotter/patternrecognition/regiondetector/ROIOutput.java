package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;

public class ROIOutput {

	// Pythagoras-G
	// 推荐参数

	public static org.opencv.core.Point abs_ulPoint;
	public static org.opencv.core.Point abs_urPoint;
	public static org.opencv.core.Point abs_llPoint;
	public static org.opencv.core.Point abs_lrPoint;

	private static org.opencv.core.Point rel_ulPoint;
	private static org.opencv.core.Point rel_urPoint;
	private static org.opencv.core.Point rel_llPoint;
	private static org.opencv.core.Point rel_lrPoint;

	public static Mat Pythagoras_G(String input) {
		// public static void main(String[] args) {
		// TODO 自动生成的方法存根
		// String input =
		// "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/1.jpg";
		// String output =
		// "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output1.jpg";

		final int roiWidth = 20;
		final int roiHeight = 20;
		final int ulx = 206;
		final int uly = 182;
		final int urx = 302;
		final int ury = 182;
		final int llx = 206;
		final int lly = 326;

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat src = Imgcodecs.imread(input);

		// Upper-Left corner
		src = CornerDetector.corners(src, ulx, uly, roiWidth, roiHeight, 10, false);
		// 求最优解
		upperLeft_optimus();
		// Imgproc.circle(CornerDetector.srcROI, rel_ulPoint, 4, new Scalar(255, 255,
		// 0), 2);
		abs_ulPoint = new org.opencv.core.Point(ulx + rel_ulPoint.x, uly + rel_ulPoint.y);

		// Upper-Right corner
		src = CornerDetector.corners(src, urx, ury, roiWidth, roiHeight, 15, false);
		// 求最优解
		upperRight_optimus();
		// Imgproc.circle(CornerDetector.srcROI, rel_urPoint, 4, new Scalar(255, 255,
		// 0), 2);
		abs_urPoint = new org.opencv.core.Point(urx + rel_urPoint.x, ury + rel_urPoint.y);

		// Lower-Left corner
		src = CornerDetector.corners(src, llx, lly, roiWidth, roiHeight, 15, false);
		// 求最优解
		lowerLeft_optimus();
		// Imgproc.circle(CornerDetector.srcROI, rel_llPoint, 4, new Scalar(255, 255,
		// 0), 2);
		abs_llPoint = new org.opencv.core.Point(llx + rel_llPoint.x, lly + rel_llPoint.y);

		// Lower-Right corner
		// abs_lrPoint =FourthCorner.fourthPoint(abs_ulPoint, abs_urPoint, abs_llPoint);
		ROIOutput.abs_lrPoint = FourthCorner.fourthPoint(ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint,
				ROIOutput.abs_llPoint);

		// Imgcodecs.imwrite(output, src);
		return src;

	}

	private static void upperLeft_optimus() {
		// 求最优解
		final double[] temp_x = new double[CornerDetector.pCorners.length];
		final double[] temp_y = new double[CornerDetector.pCorners.length];
		for (int i = 0; i < CornerDetector.pCorners.length; i++) {
			temp_x[i] = CornerDetector.pCorners[i].x;
			temp_y[i] = CornerDetector.pCorners[i].y;
		}
		final double ulpoint_x = GetMaxMinMidAvg.getMinFromArray(temp_x);
		final double ulpoint_y = GetMaxMinMidAvg.getMidFromArray(temp_y);
		rel_ulPoint = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		// System.out.println(rel_ulPoint.x + "," + rel_ulPoint.y);
	}

	private static void upperRight_optimus() {
		// 求最优解
		final double[] temp_x = new double[CornerDetector.pCorners.length];
		final double[] temp_y = new double[CornerDetector.pCorners.length];
		for (int i = 0; i < CornerDetector.pCorners.length; i++) {
			temp_x[i] = CornerDetector.pCorners[i].x;
			temp_y[i] = CornerDetector.pCorners[i].y;
		}
		final double ulpoint_x = GetMaxMinMidAvg.getMidFromArray(temp_x);
		final double ulpoint_y = GetMaxMinMidAvg.getMinFromArray(temp_y);
		rel_urPoint = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		// System.out.println(rel_urPoint.x + "," + rel_urPoint.y);
	}

	private static void lowerLeft_optimus() {
		// 求最优解
		final double[] temp_x = new double[CornerDetector.pCorners.length];
		final double[] temp_y = new double[CornerDetector.pCorners.length];
		for (int i = 0; i < CornerDetector.pCorners.length; i++) {
			temp_x[i] = CornerDetector.pCorners[i].x;
			temp_y[i] = CornerDetector.pCorners[i].y;
		}
		final double ulpoint_x = GetMaxMinMidAvg.getMinFromArray(temp_x);
		final double ulpoint_y = GetMaxMinMidAvg.getMinFromArray(temp_y);
		rel_llPoint = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		// System.out.println(rel_llPoint.x + "," + rel_llPoint.y);
	}

	private static void lowerRight_optimus() {
		// 求最优解
		final double[] temp_x = new double[CornerDetector.pCorners.length];
		final double[] temp_y = new double[CornerDetector.pCorners.length];
		for (int i = 0; i < CornerDetector.pCorners.length; i++) {
			temp_x[i] = CornerDetector.pCorners[i].x;
			temp_y[i] = CornerDetector.pCorners[i].y;
		}
		final double ulpoint_x = GetMaxMinMidAvg.getAvgFromArray(temp_x);
		final double ulpoint_y = GetMaxMinMidAvg.getMidFromArray(temp_y);
		rel_lrPoint = new org.opencv.core.Point(ulpoint_x, ulpoint_y);
		// System.out.println(rel_lrPoint.x + "," + rel_lrPoint.y);
	}

}
