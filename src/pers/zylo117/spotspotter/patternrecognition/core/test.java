package pers.zylo117.spotspotter.patternrecognition.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.gui.viewer.BImgView;
import pers.zylo117.spotspotter.gui.viewer.MatView;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.SetPixelArray;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.mathBox.Line;

public class test {
	// Default: sigma = 0.33
	public static Mat autoCanny(Mat image, double sigma, boolean ifBlur) {
		// MatView.imshow(image, "MAT");
		// Mat pyS = new Mat();
		// Imgproc.pyrMeanShiftFiltering(image, pyS, 21, 51);
		// MatView.imshow(pyS, "py1");

		byte[] array = GetPixelArray.getRaster(image);
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
			median = (intRaseter.get(intRaseter.size() / 2 - 1) + intRaseter.get(intRaseter.size())) / 2;
		else
			median = intRaseter.get(intRaseter.size() / 2);

		int lower = (int) (0 < ((1.0 - sigma) * median) ? ((1.0 - sigma) * median) : 0);
		int upper = (int) (255 > ((1.0 + sigma) * median) ? ((1.0 + sigma) * median) : 255);

		Mat blurred = new Mat();
		Mat edge = Mat.zeros(image.size(), CvType.CV_8UC1);
		if(ifBlur) {
			Imgproc.GaussianBlur(image, blurred, new Size(11, 11), 0);
			Imgproc.Canny(blurred, edge, lower, upper);
		}
		else
			Imgproc.Canny(image, edge, lower, upper);
		
		System.out.println(median);
		System.out.println(lower);
		System.out.println(upper);
		MatView.imshow(edge, "Edge");
		
		return edge;
	}

	public static void findLargestContour(Mat input) {
		List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();
		Imgproc.findContours(input.clone(), cnts, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		Imgproc.drawContours(input, cnts, -1, new Scalar(255, 0, 0), 4);
		MatView.imshow(input, "Img");
	}

	public static void main(String[] args) {
		System.loadLibrary("opencv_java330_64");
		String input = "D:\\tmp\\7.jpg";
		Mat image = Imgcodecs.imread(input);
//		Mat edge = autoCanny(image, 0.67, false);
		Mat edge_blurred = autoCanny(image, 0.67, true);
		findLargestContour(edge_blurred);
	}

}
