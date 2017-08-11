package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

public class LineDetector {
	public static void LSD(Mat in, Mat out) {
		LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
		Mat _lines = new Mat();
		lsd.detect(in, _lines); 
		lsd.drawSegments(out, _lines);
	}

	public static void LD(Mat in, Mat out) {
		LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
	}

	public static void main(String[] args) throws Exception {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/3.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output3.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat in = Imgcodecs.imread(input);
		if (in.empty()) {
			throw new Exception("no file");
		}
		Mat gray = new Mat();
		
		Imgproc.cvtColor(in, gray, Imgproc.COLOR_RGB2GRAY);
		Mat out = gray.clone();
		
		LSD(gray, out);
		Imgcodecs.imwrite(output, out);
	}
}
