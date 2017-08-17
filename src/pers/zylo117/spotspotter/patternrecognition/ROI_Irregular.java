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

import pers.zylo117.spotspotter.viewer.MatView;

public class ROI_Irregular {

	// public static Mat irregular_ROI(Mat imgOrigin) {
	// Mat output = Circle(imgOrigin);
	// return output;
	// }

	public static void main(String[] args) {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/5.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output5.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Point p1 = new Point(216, 187);
		Point p2 = new Point(313, 187);
		Point p3 = new Point(216, 337);
		Point p4 = new Point(313, 337);

		Mat imgOrigin = Imgcodecs.imread(input);
		Point a = new Point(256, 256);
		// Mat roi = Circle(imgOrigin, a, 165);
		Mat roi = irregularQuadrangle(imgOrigin, p1, p2, p3, p4, 3, false , 0 , 0);
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

		double sin = Math.sin(RotationAngle.slope(p1, p2));
		double cos = Math.cos(RotationAngle.slope(p1, p2));
		
		List<MatOfPoint> counter = new ArrayList<>();
		Point aP1 = new Point(p1.x + shift * cos + shift * sin, p1.y + shift * cos - shift * sin);
		Point aP2 = new Point(p2.x - shift * cos + shift * sin, p2.y + shift * cos + shift * sin);
		Point aP3 = new Point(p3.x + shift * cos - shift * sin, p3.y - shift * cos - shift * sin);
		Point aP4 = new Point(p4.x - shift * cos - shift * sin, p4.y - shift * cos + shift * sin);

		if (noULandLRcorner) {
			Point aP1L = new Point(aP1.x, aP1.y * (aP3.y - aP1.y) * ulCornerRatio);
			
			counter.add(new MatOfPoint(aP1, aP2, aP4, aP3)); // ����һ��������Ķ���Σ�û�����ϽǺ����½�
		} else
			counter.add(new MatOfPoint(aP1, aP2, aP4, aP3)); // ����һ��������Ķ����

		// �����ĵ���Ϊ��ʼ����
		double centerX = (p1.x + p2.x + p3.x + p4.x) / 4;
		double centerY = (p1.y + p2.y + p3.y + p4.y) / 4;

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
