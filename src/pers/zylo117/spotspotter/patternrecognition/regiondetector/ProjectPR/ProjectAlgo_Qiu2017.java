package pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR;

import java.awt.image.BufferedImage;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.Binaryzation;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.SetPixelArray;
import pers.zylo117.spotspotter.toolbox.BufferedImage2Mat;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

// Enlightened by YuNing.Qiu, Postgraduate of GDUT, a great brother
public class ProjectAlgo_Qiu2017 {
	public static int[][] colorTrend_Mat(Mat input, boolean ifGray) {
		BufferedImage bimg = Mat2BufferedImage.mat2BI(input);
		int[][] singleChannelData = GetPixelArray.pixelArray(bimg, ifGray);

		// for (int i = 0; i < input.width(); i++) {
		// System.out.printf("%x\t", singleChannelData[i][256]);
		// }
		return singleChannelData;
	}

	public static int leapPoint_fromCenter(int[] input, double thresh, boolean ifToLeft) {
		int center = input.length / 2;
		int lP = 0;
		if (ifToLeft) {
			for (int i = 1; i < center; i++) {
				if ((input[center - i] > thresh * input[center - i + 1])) {
					lP = center - i;
					break;
				}
			}
		} else {
			for (int i = 1; i < center; i++) {
				if ((input[center + i] > thresh * input[center + i - 1])) {
					lP = center + i;
					break;
				}
			}
		}
		return lP;
	}

	public static void colorProject_Qiu2017(Mat imgOrigin, double thresh) {
//	public static void main(String[] args) throws IOException {
//		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output7.jpg";
//		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output7.jpg";
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		Mat imgOrigin = Imgcodecs.imread(input);
//		MatView.imshow(imgOrigin, "Original Image");
		
		// ¶þÖµ»¯Í¼Æ¬
		imgOrigin = Binaryzation.binaryzation_OpenCV(imgOrigin, thresh);
		
		int[][] data = colorTrend_Mat(imgOrigin, false);
		BufferedImage bimg = SetPixelArray.fromPixelArray(data, imgOrigin.width(), imgOrigin.height());
//		BIView.imshow(bimg, "out");
		Mat out = BufferedImage2Mat.img2Mat(bimg);
		// Imgcodecs.imwrite(output, out);
		// ImageStream2File.IS2F(bimg, "jpg", output);

		int[][] datasingle = colorTrend_Mat(imgOrigin, true);
		// for (int i = 0; i < datasingle.length; i++) {
		// System.out.println(datasingle[i][256]);
		// }
		
		int lP_up = leapPoint_fromCenter(datasingle[datasingle.length / 2], thresh, true);
		int lP_down = leapPoint_fromCenter(datasingle[datasingle.length / 2], thresh, false);
		
		int[] horizon = new int[datasingle.length];
		for(int i=1;i<datasingle.length;i++) {
			
			horizon[i] = datasingle[i][datasingle[datasingle.length / 2].length / 2];
		}
		int lP_left = leapPoint_fromCenter(horizon, thresh, true);
		
		int lP_right = leapPoint_fromCenter(horizon, thresh, false);

//		Imgproc.circle(out, new Point(datasingle.length / 2, lP_up), 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, new Point(datasingle.length / 2, lP_down), 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, new Point(lP_left,datasingle[datasingle.length / 2].length / 2), 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, new Point(lP_right,datasingle[datasingle.length / 2].length / 2), 4, new Scalar(255, 255, 0), 2);
		
		ulP = new Point(lP_left,lP_up);
		urP = new Point(lP_right,lP_up);
		llP = new Point(lP_left,lP_down);
		lrP = new Point(lP_right,lP_down);
//		
//		Imgproc.circle(out, ulP, 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, urP, 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, llP, 4, new Scalar(255, 255, 0), 2);
//		Imgproc.circle(out, lrP, 4, new Scalar(255, 255, 0), 2);
		
//		MatView.imshow(out, "mat");
	}
	
	public static Point ulP;
	public static Point urP;
	public static Point llP;
	public static Point lrP;
}
