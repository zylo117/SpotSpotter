package pers.zylo117.spotspotter.pictureprocess.drawer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.regiondetector.ROIOutput;

public class DrawROI {
//	public static void DrawROI(String input, String output) throws Exception {
		public static void main(String[] args) throws Exception {
			String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/8.jpg";
			String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output8.jpg";
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			Mat in = ROIOutput.Pythagoras_G(input);
			if (in.empty()) {
				throw new Exception("no file");
			}

			System.out.println(ROIOutput.abs_ulPoint.x + "," + ROIOutput.abs_ulPoint.y);
			System.out.println(ROIOutput.abs_urPoint.x + "," + ROIOutput.abs_urPoint.y);
			System.out.println(ROIOutput.abs_llPoint.x + "," + ROIOutput.abs_llPoint.y);
			System.out.println(ROIOutput.abs_lrPoint.x + "," + ROIOutput.abs_lrPoint.y);

			Imgproc.circle(in, ROIOutput.abs_ulPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_urPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_llPoint, 4, new Scalar(255, 255, 0), 1);
			Imgproc.circle(in, ROIOutput.abs_lrPoint, 4, new Scalar(255, 255, 0), 1);

			Mat out = new Mat();
			Draw.line_P2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint);
			Draw.line_P2P(in, ROIOutput.abs_ulPoint, ROIOutput.abs_llPoint);
			Draw.line_P2P(in, ROIOutput.abs_llPoint, ROIOutput.abs_lrPoint);
			Draw.line_P2P(in, ROIOutput.abs_lrPoint, ROIOutput.abs_urPoint);

			Imgcodecs.imwrite(output, out);
			
//			Point a1 = new Point(0,0);
//			Point a2 = new Point(0,1);
//			double angle = MathBox.slopeAngle(a1, a2);
//			System.out.println(angle);
//			
//			Line line1 = new Line(new Point(0,0), new Point(1,0));
//			Line line2 = new Line(new Point(1,1), new Point(2,1));
//			double l = MathBox.lineDistance(line1,line2);
//			System.out.println(l);
//			
//			System.out.println(AngleTransform.radian2Angle(-3*Math.PI));
//			
//			Point a1 = new Point(0,0);
//			Point a2 = new Point(1,0);
//			Point a3 = MathBox.rotateAroundAPoint(a1, a2, 90);
//			System.out.println(a3.x + "   " +a3.y);
		}
}
