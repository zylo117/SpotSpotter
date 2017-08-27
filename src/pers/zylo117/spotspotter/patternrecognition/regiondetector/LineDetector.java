package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

public class LineDetector {
	public static Mat LSD(Mat in) {
		LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
		Mat gray = new Mat();
		Imgproc.cvtColor(in, gray, Imgproc.COLOR_RGB2GRAY);
		Mat out = gray.clone();
		Mat _lines = new Mat();
		lsd.detect(gray, _lines); 
		lsd.drawSegments(out, _lines);
		return out;
	}


	public static void main(String[] args) throws Exception {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/4.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output4.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat in = Imgcodecs.imread(input);
		if (in.empty()) {
			throw new Exception("no file");
		}
		Mat out = LSD(in);
		Imgcodecs.imwrite(output, out);
	}
}
