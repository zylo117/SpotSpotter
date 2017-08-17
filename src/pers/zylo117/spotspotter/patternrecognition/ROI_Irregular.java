package pers.zylo117.spotspotter.patternrecognition;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.regiondetector.ROIOutput;
import pers.zylo117.spotspotter.toolbox.MathBox.MathBox;
import pers.zylo117.spotspotter.viewer.MatView;

public class ROI_Irregular {

	// public static Mat irregular_ROI(Mat imgOrigin) {
	// Mat output = Circle(imgOrigin);
	// return output;
	// }

	public static void main(String[] args) {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/6.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output6.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat imgOrigin = ROIOutput.Pythagoras_G(input);
		Mat roi = irregularQuadrangle(imgOrigin, ROIOutput.abs_ulPoint, ROIOutput.abs_urPoint, ROIOutput.abs_llPoint,
				ROIOutput.abs_lrPoint, 4, true, 0.1, 0.2);
		MatView.imshow(imgOrigin, "Original Image");
		MatView.imshow(roi, "ROI");
	}

	public static Mat Rectangle(Mat rectInput, int startX, int startY, int width, int height) {
		Rect rect = new Rect(startX, startY, width, height); // ���þ���ROI��λ��
		Mat imgRectROI = new Mat(rectInput, rect); // ��ԭͼ�н�ȡͼƬ
		// MatView.imshow(imgRectROI, "Rectangle ROI"); // ��ʾ��ȡ���ͼƬ
		return imgRectROI;
	}

	public static Mat Circle(Mat circularInput, Point cc, int radius) {
		Mat maskCopyTo = Mat.zeros(circularInput.size(), CvType.CV_8UC1); // ����copyTo������mask����С��ԭͼ����һ��
		// floodFill��mask��width��height�����������ͼ��������������أ��������ᱨ��
		Mat maskFloodFill = Mat.zeros(new Size(circularInput.cols() + 2, circularInput.rows() + 2), CvType.CV_8UC1); // ����floodFill������mask���ߴ��ԭͼ��һЩ
		Imgproc.circle(maskCopyTo, new Point(cc.x, cc.y), radius, Scalar.all(255), 2, 8, 0); // ����Բ������
		Imgproc.floodFill(maskCopyTo, maskFloodFill, new Point(207, 290), Scalar.all(255), null, Scalar.all(20),
				Scalar.all(20), 4); // ��ˮ��䷨���Բ���ڲ�
		// MatView.imshow(maskFloodFill, "Mask of floodFill"); // ����floodFill������mask
		// MatView.imshow(maskCopyTo, "Mask of copyTo"); // ����copyTo������mask
		Mat imgCircularROI = new Mat();
		circularInput.copyTo(imgCircularROI, maskCopyTo); // ��ȡԲ�ε�ROI
		// MatView.imshow(imgCircularROI, "Circular ROI"); // ��ʾԲ�ε�ROI
		return imgCircularROI;
	}

	public static Mat irregularQuadrangle(Mat irrInput, Point p1, Point p2, Point p3, Point p4, double shift,
			boolean noULandLRcorner, double ulCornerRatio, double lrCornerRatio) {
		Mat maskCopyTo = Mat.zeros(irrInput.size(), CvType.CV_8UC1); // ����copyTo������mask����С��ԭͼ����һ��

		double angle = MathBox.slopeAngle(p1, p2);
//		System.out.println(angle);
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);

		// �����ĵ���Ϊ��ʼ����
		double centerX = (p1.x + p2.x + p3.x + p4.x) / 4;
		double centerY = (p1.y + p2.y + p3.y + p4.y) / 4;
		Point cc = new Point(centerX, centerY);

		List<MatOfPoint> counter = new ArrayList<>();
		Point aP1raw = new Point(p1.x + shift, p1.y + shift);
		Point aP2raw = new Point(p2.x - shift, p2.y + shift);
		Point aP3raw = new Point(p3.x + shift, p3.y - shift);
		Point aP4raw = new Point(p4.x - shift, p4.y - shift);

		Point aP1 = MathBox.rotateAroundAPoint(cc, aP1raw, angle);
		Point aP2 = MathBox.rotateAroundAPoint(cc, aP2raw, angle);
		Point aP3 = MathBox.rotateAroundAPoint(cc, aP3raw, angle);
		Point aP4 = MathBox.rotateAroundAPoint(cc, aP4raw, angle);

		if (noULandLRcorner) {
			Point aP1Lraw = new Point(aP1.x, aP1.y + (aP3.y - aP1.y) * ulCornerRatio);
			Point aP1Rraw = new Point(aP1.x + (aP2.x - aP1.x) * ulCornerRatio, aP1.y);
			Point aP4Lraw = new Point(aP4.x - (aP4.x - aP3.x) * lrCornerRatio, aP4.y);
			Point aP4Rraw = new Point(aP4.x, aP4.y - (aP4.y - aP2.y) * ulCornerRatio);
			
			Point aP1L = MathBox.rotateAroundAPoint(cc, aP1Lraw, angle);
			Point aP1R = MathBox.rotateAroundAPoint(cc, aP1Rraw, angle);
			Point aP4L = MathBox.rotateAroundAPoint(cc, aP4Lraw, angle);
			Point aP4R = MathBox.rotateAroundAPoint(cc, aP4Rraw, angle);
			
			counter.add(new MatOfPoint(aP1L, aP1R, aP2, aP4R,aP4L, aP3)); // ����һ��������Ķ���Σ�û�����ϽǺ����½�
		} else
			counter.add(new MatOfPoint(aP1, aP2, aP4, aP3)); // ����һ��������Ķ����

		// floodFill��mask��width��height�����������ͼ��������������أ��������ᱨ��
		Imgproc.drawContours(maskCopyTo, counter, -1, Scalar.all(255)); // ��������
		MatView.imshow(maskCopyTo, "Irregular shape edge");
		Mat maskFloodFill = new Mat(irrInput.rows() + 2, irrInput.cols() + 2, CvType.CV_8UC1); // ����floodFill������mask���ߴ��ԭͼ��һЩ
		Imgproc.floodFill(maskCopyTo, maskFloodFill, new Point(centerX, centerY), Scalar.all(255), null, Scalar.all(20),
				Scalar.all(20), 4); // ��ˮ��䷨����ڲ�
		// MatView.imshow(maskFloodFill, "Irregular shape��Mask of floodFill"); //
		// ����copyTo������mask
		// MatView.imshow(maskCopyTo, "Irregular shape��Mask of copyTo"); //
		// ����floodFill������mask
		Mat imgIrregularROI = new Mat();
		irrInput.copyTo(imgIrregularROI, maskCopyTo); // ��ȡ��������״��ROI
		// MatView.imshow(imgIrregularROI, "Irregular shape ROI");
		return imgIrregularROI;
	}
}