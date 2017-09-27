package pers.zylo117.spotspotter.patternrecognition;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.gui.viewer.MatView;
import pers.zylo117.spotspotter.toolbox.mathBox.MathBox;

/***
 * ROI�Ľ���������
 * 
 * @param p1
 *            p2
 * @param p3
 *            p4
 */

public class ROI_Irregular {
	public static Mat Rectangle(Mat rectInput, int startX, int startY, int width, int height) {
		final Rect rect = new Rect(startX, startY, width, height); // ���þ���ROI��λ��
		final Mat imgRectROI = new Mat(rectInput, rect); // ��ԭͼ�н�ȡͼƬ
		// MatView.imshow(imgRectROI, "Rectangle ROI"); // ��ʾ��ȡ���ͼƬ
		return imgRectROI;
	}

	public static Mat Circle(Mat circularInput, Point cc, int radius) {
		final Mat maskCopyTo = Mat.zeros(circularInput.size(), CvType.CV_8UC1); // ����copyTo������mask����С��ԭͼ����һ��
		// floodFill��mask��width��height�����������ͼ��������������أ��������ᱨ��
		final Mat maskFloodFill = Mat.zeros(new Size(circularInput.cols() + 2, circularInput.rows() + 2), CvType.CV_8UC1); // ����floodFill������mask���ߴ��ԭͼ��һЩ
		Imgproc.circle(maskCopyTo, new Point(cc.x, cc.y), radius, Scalar.all(255), 2, 8, 0); // ����Բ������
		Imgproc.floodFill(maskCopyTo, maskFloodFill, new Point(207, 290), Scalar.all(255), null, Scalar.all(20),
				Scalar.all(20), 4); // ��ˮ��䷨���Բ���ڲ�
		// MatView.imshow(maskFloodFill, "Mask of floodFill"); // ����floodFill������mask
		// MatView.imshow(maskCopyTo, "Mask of copyTo"); // ����copyTo������mask
		final Mat imgCircularROI = new Mat();
		circularInput.copyTo(imgCircularROI, maskCopyTo); // ��ȡԲ�ε�ROI
		// MatView.imshow(imgCircularROI, "Circular ROI"); // ��ʾԲ�ε�ROI
		return imgCircularROI;
	}

	public static Mat irregularQuadrangle(Mat irrInput, Point p1, Point p2, Point p3, Point p4, double shift,
			boolean noULandLRcorner, double ulCornerRatio, double lrCornerRatio) {
		final Mat maskCopyTo = Mat.zeros(irrInput.size(), CvType.CV_8UC1); // ����copyTo������mask����С��ԭͼ����һ��

		final double angle = MathBox.slopeAngle(p1, p2);
		// System.out.println(angle);
		final double sin = Math.sin(angle);
		final double cos = Math.cos(angle);

		// �����ĵ���Ϊ��ʼ����
		final double centerX = (p1.x + p2.x + p3.x + p4.x) / 4;
		final double centerY = (p1.y + p2.y + p3.y + p4.y) / 4;
		final Point cc = new Point(centerX, centerY);

		final List<MatOfPoint> counter = new ArrayList<>();
		final Point aP1raw = new Point(p1.x + shift, p1.y + shift);
		final Point aP2raw = new Point(p2.x - shift, p2.y + shift);
		final Point aP3raw = new Point(p3.x + shift, p3.y - shift);
		final Point aP4raw = new Point(p4.x - shift, p4.y - shift);

		final Point aP1 = MathBox.rotateAroundAPoint(cc, aP1raw, angle);
		final Point aP2 = MathBox.rotateAroundAPoint(cc, aP2raw, angle);
		final Point aP3 = MathBox.rotateAroundAPoint(cc, aP3raw, angle);
		final Point aP4 = MathBox.rotateAroundAPoint(cc, aP4raw, angle);

		if (noULandLRcorner) {
			final Point aP1Lraw = new Point(aP1.x, aP1.y + (aP3.y - aP1.y) * ulCornerRatio);
			final Point aP1Rraw = new Point(aP1.x + (aP2.x - aP1.x) * ulCornerRatio, aP1.y);
			final Point aP4Lraw = new Point(aP4.x - (aP4.x - aP3.x) * lrCornerRatio, aP4.y);
			final Point aP4Rraw = new Point(aP4.x, aP4.y - (aP4.y - aP2.y) * lrCornerRatio);

			final Point aP1L = MathBox.rotateAroundAPoint(cc, aP1Lraw, angle);
			final Point aP1R = MathBox.rotateAroundAPoint(cc, aP1Rraw, angle);
			final Point aP4L = MathBox.rotateAroundAPoint(cc, aP4Lraw, angle);
			final Point aP4R = MathBox.rotateAroundAPoint(cc, aP4Rraw, angle);

			counter.add(new MatOfPoint(aP1L, aP1R, aP2, aP4R, aP4L, aP3)); // ����һ��������Ķ���Σ�û�����ϽǺ����½�
		} else
			counter.add(new MatOfPoint(aP1, aP2, aP4, aP3)); // ����һ��������Ķ����

		// floodFill��mask��width��height�����������ͼ��������������أ��������ᱨ��
		Imgproc.drawContours(maskCopyTo, counter, -1, Scalar.all(255)); // ��������
		MatView.imshow(maskCopyTo, "Irregular shape edge");
		final Mat maskFloodFill = new Mat(irrInput.rows() + 2, irrInput.cols() + 2, CvType.CV_8UC1); // ����floodFill������mask���ߴ��ԭͼ��һЩ
		Imgproc.floodFill(maskCopyTo, maskFloodFill, new Point(centerX, centerY), Scalar.all(255), null, Scalar.all(20),
				Scalar.all(20), 4); // ��ˮ��䷨����ڲ�
		// MatView.imshow(maskFloodFill, "Irregular shape��Mask of floodFill"); //
		// ����copyTo������mask
		// MatView.imshow(maskCopyTo, "Irregular shape��Mask of copyTo"); //
		// ����floodFill������mask
		final Mat imgIrregularROI = new Mat();
		irrInput.copyTo(imgIrregularROI, maskCopyTo); // ��ȡ��������״��ROI
		// MatView.imshow(imgIrregularROI, "Irregular shape ROI");
		return imgIrregularROI;
	}

	public static Mat irregularQuadrangle_Simplified(Mat irrInput, Point p1, Point p2, Point p3, Point p4, double shift_x, double shift_y,
			boolean noULandLRcorner, double ulCornerRatio, double lrCornerRatio) {
		final Mat maskCopyTo = Mat.zeros(irrInput.size(), CvType.CV_8UC1); // ����copyTo������mask����С��ԭͼ����һ��

		// �����ĵ���Ϊ��ʼ����
		final double centerX = (p1.x + p2.x + p3.x + p4.x) / 4;
		final double centerY = (p1.y + p2.y + p3.y + p4.y) / 4;
		final Point cc = new Point(centerX, centerY);

		final List<MatOfPoint> counter = new ArrayList<>();
		final Point aP1 = new Point(p1.x + shift_x, p1.y + shift_y);
		final Point aP2 = new Point(p2.x - shift_x, p2.y + shift_y);
		final Point aP3 = new Point(p3.x + shift_x, p3.y - shift_y);
		final Point aP4 = new Point(p4.x - shift_x, p4.y - shift_y);

		if (noULandLRcorner) {
			final Point aP1L = new Point(aP1.x, aP1.y + (aP3.y - aP1.y) * ulCornerRatio);
			final Point aP1R = new Point(aP1.x + (aP2.x - aP1.x) * ulCornerRatio, aP1.y);
			final Point aP4L = new Point(aP4.x - (aP4.x - aP3.x) * lrCornerRatio, aP4.y);
			final Point aP4R = new Point(aP4.x, aP4.y - (aP4.y - aP2.y) * lrCornerRatio);

			counter.add(new MatOfPoint(aP1L, aP1R, aP2, aP4R, aP4L, aP3)); // ����һ��������Ķ���Σ�û�����ϽǺ����½�
		} else
			counter.add(new MatOfPoint(aP1, aP2, aP4, aP3)); // ����һ��������Ķ����

		// floodFill��mask��width��height�����������ͼ��������������أ��������ᱨ��
		Imgproc.drawContours(maskCopyTo, counter, -1, Scalar.all(255)); // ��������
//		MatView.imshow(maskCopyTo, "Irregular shape edge");
		final Mat maskFloodFill = new Mat(irrInput.rows() + 2, irrInput.cols() + 2, CvType.CV_8UC1); // ����floodFill������mask���ߴ��ԭͼ��һЩ
		Imgproc.floodFill(maskCopyTo, maskFloodFill, new Point(centerX, centerY), Scalar.all(255), null, Scalar.all(20),
				Scalar.all(20), 4); // ��ˮ��䷨����ڲ�
		// MatView.imshow(maskFloodFill, "Irregular shape��Mask of floodFill"); //
		// ����copyTo������mask
		// MatView.imshow(maskCopyTo, "Irregular shape��Mask of copyTo"); //
		// ����floodFill������mask
		final Mat imgIrregularROI = new Mat();
		irrInput.copyTo(imgIrregularROI, maskCopyTo); // ��ȡ��������״��ROI
		// MatView.imshow(imgIrregularROI, "Irregular shape ROI");
		return imgIrregularROI;
	}

//	public static Mat zoomoutMat(Mat input, Point p1, Point p2, Point p3, Point p4, double scale_x, double scale_y) {
//		double width = p2.x - p1.x;
//		double height = p3.y - p1.y;
//		Point newP1 = new Point(p1.x + width * scale_x, p1.y + height*scale_y);
//		Point newP2 = new Point(p2.x - width * scale_x, p2.y + height*scale_y);
//		Point newP3 = new Point(p3.x + width * scale_x, p3.y - height*scale_y);
//		Point newP4 = new Point(p4.x - width * scale_x, p4.y - height*scale_y);
//		
//		Mat out = irregularQuadrangle_Simplified(input, newP1, newP2, newP3, newP4, shift, noULandLRcorner, ulCornerRatio, lrCornerRatio)
//		return null;
//	}
}
