package pers.zylo117.pictureprocess.sharpening;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Laplacian {
	public static void laplacian(String input, String output, int ddepth) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat src = Imgcodecs.imread(input);

			// ��ȡͼ�񵽾���
			if (src.empty()) {
				throw new Exception("no file");
			}

			Mat dst = src.clone();
			// ���ƾ���dst
			Imgproc.Laplacian(src, dst, ddepth);
			Imgcodecs.imwrite(output, dst);
		} catch (Exception e) {
			System.out.println("Exception " + e);
		}
	}
}
