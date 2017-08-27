// Canny(Mat image, Mat edges, double threshold1, double threshold2, int
// apertureSize, boolean L2gradient)
// ��һ��������InputArray���͵�image������ͼ�񣬼�Դͼ����Mat��Ķ��󼴿ɣ�����Ϊ��ͨ��8λͼ��
// �ڶ���������OutputArray���͵�edges������ı�Եͼ����Ҫ��ԴͼƬ��һ���ĳߴ�����͡�
// ������������double���͵�threshold1����һ���ͺ�����ֵ��
// ���ĸ�������double���͵�threshold2���ڶ����ͺ�����ֵ��
// �����������int���͵�apertureSize����ʾӦ��Sobel���ӵĿ׾���С������Ĭ��ֵ3��
// ������������bool���͵�L2gradient��һ������ͼ���ݶȷ�ֵ�ı�ʶ����Ĭ��ֵfalse��

package pers.zylo117.spotspotter.patternrecognition.edgedetector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class CannyEdgeDetector {
	public static void canny(String oriImg, String dstImg, int threshold) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = Imgcodecs.imread(oriImg);
		Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
		//
		Imgproc.Canny(img, img, threshold, threshold * 3, 3, true);
		//
		Imgcodecs.imwrite(dstImg, img);
	}

}
