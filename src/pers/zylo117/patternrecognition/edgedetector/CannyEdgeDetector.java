// Canny(Mat image, Mat edges, double threshold1, double threshold2, int
// apertureSize, boolean L2gradient)
// 第一个参数，InputArray类型的image，输入图像，即源图像，填Mat类的对象即可，且需为单通道8位图像。
// 第二个参数，OutputArray类型的edges，输出的边缘图，需要和源图片有一样的尺寸和类型。
// 第三个参数，double类型的threshold1，第一个滞后性阈值。
// 第四个参数，double类型的threshold2，第二个滞后性阈值。
// 第五个参数，int类型的apertureSize，表示应用Sobel算子的孔径大小，其有默认值3。
// 第六个参数，bool类型的L2gradient，一个计算图像梯度幅值的标识，有默认值false。

package pers.zylo117.patternrecognition.edgedetector;

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
