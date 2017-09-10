package pers.zylo117.spotspotter.pictureprocess;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Resize {
	public static Mat tillFit(Mat input, double targetX, double targetY) {
		Mat inputClone = input.clone();
		double newWidth = input.width();
		double newHeight = input.height();
		while (newWidth > targetX || newHeight > targetY) {
			Imgproc.resize(input, inputClone, new Size(newWidth / 2, newHeight / 2));
			newWidth /= 2;
			newHeight /= 2;
		}
		return inputClone;
	}
}
