package pers.zylo117.spotspotter.pictureprocess.drawer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.regiondetector.FourthCorner;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ROIOutput;

public class DrawROI {
	public static void DrawROI(String input, String output) throws Exception {
//		public static void main(String[] args) throws Exception {
//			String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/6.jpg";
//			String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output6.jpg";
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat in = ROIOutput.Pythagoras_G(input);
			if (in.empty()) {
				throw new Exception("no file");
			}

			ROIOutput.abs_lrPoint = FourthCorner.fourthPoint(ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint,
					ROIOutput.abs_llPoint);

			System.out.println(ROIOutput.abs_ulPoint.x + "," + ROIOutput.abs_ulPoint.y);
			System.out.println(ROIOutput.abs_urPoint.x + "," + ROIOutput.abs_urPoint.y);
			System.out.println(ROIOutput.abs_llPoint.x + "," + ROIOutput.abs_llPoint.y);
			System.out.println(ROIOutput.abs_lrPoint.x + "," + ROIOutput.abs_lrPoint.y);

			Imgproc.circle(in, ROIOutput.abs_ulPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_urPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_llPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_lrPoint, 4, new Scalar(255, 255, 0), 1);

			Mat out = new Mat();
			out = DrawLine.lineP2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint);
			out = DrawLine.lineP2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_llPoint);
			out = DrawLine.lineP2P(in, ROIOutput.abs_llPoint, ROIOutput.abs_lrPoint);
			out = DrawLine.lineP2P(in, ROIOutput.abs_lrPoint, ROIOutput.abs_urPoint);

			Imgcodecs.imwrite(output, out);
		}
}
