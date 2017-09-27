package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

public class LineDetector {
	public static Mat LSD(Mat in) {
		final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
		final Mat gray = new Mat();
		Imgproc.cvtColor(in, gray, Imgproc.COLOR_RGB2GRAY);
		final Mat out = gray.clone();
		final Mat _lines = new Mat();
		lsd.detect(gray, _lines); 
		lsd.drawSegments(out, _lines);
		return out;
	}


	public static void main(String[] args) throws Exception {
		final String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/4.jpg";
		final String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output4.jpg";

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		final Mat in = Imgcodecs.imread(input);
		if (in.empty()) {
			throw new Exception("no file");
		}
		final Mat out = LSD(in);
		Imgcodecs.imwrite(output, out);
	}
}
