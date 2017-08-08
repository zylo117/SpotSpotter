package pers.zylo117.spotspotter.toolbox;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class File2Matrix {
	public static Mat F2M(String input) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat image = Imgcodecs.imread(input);
		return image;
	}
}
