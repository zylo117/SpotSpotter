package pers.zylo117.spotspotter.viewer;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageViewer {
	public static void main(String[] args) {
		String input = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\face.jpg";
		String output = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\faceoutput.jpg";

		Mat srcImage = Imgcodecs.imread(input);
		
		
	}
}
