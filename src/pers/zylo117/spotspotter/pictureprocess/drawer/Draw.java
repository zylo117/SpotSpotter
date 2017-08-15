package pers.zylo117.spotspotter.pictureprocess.drawer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.regiondetector.ROIOutput;

public class Draw {

	// 点到点画线
	public static Mat lineP2P(Mat inputmat, org.opencv.core.Point startP, org.opencv.core.Point endP) {
		Imgproc.line(inputmat, startP, endP, new Scalar(255,255,0), 1);
		return inputmat;
	}
	
	public static void main(String[] args) throws Exception {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/5.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output5.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat in = ROIOutput.Pythagoras_G(input);
		if (in.empty()) {
			throw new Exception("no file");
		}
		
		Mat out = lineP2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint);
		out = lineP2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_llPoint);
		Imgcodecs.imwrite(output, out);
		
	}
}
