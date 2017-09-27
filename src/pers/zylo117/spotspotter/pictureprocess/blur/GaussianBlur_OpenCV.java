package pers.zylo117.spotspotter.pictureprocess.blur;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class GaussianBlur_OpenCV {

	public static void blur(String input, String output) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			final Mat src = Imgcodecs.imread(input);
			// ��ȡͼ�񵽾�����
			if (src.empty()) {
				throw new Exception("no file");
			}

			final Mat dst = src.clone();
			// ���ƾ������dst

			Imgproc.GaussianBlur(src, dst, new Size(13, 13), 0, 0);
			// ͼ��ģ��������
			Imgcodecs.imwrite(output, dst);

		} catch (final Exception e) {
			System.out.println("Exception: " + e);
		}

	}

}