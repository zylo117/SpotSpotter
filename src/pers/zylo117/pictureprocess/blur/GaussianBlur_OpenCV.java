package pers.zylo117.pictureprocess.blur;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;

public class GaussianBlur_OpenCV {

	public static void blur(String input, String output) {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			Mat src = Imgcodecs.imread(input);
			// 读取图像到矩阵中
			if (src.empty()) {
				throw new Exception("no file");
			}

			Mat dst = src.clone();
			// 复制矩阵进入dst

			Imgproc.GaussianBlur(src, dst, new Size(13, 13), 0, 0);
			// 图像模糊化处理
			Imgcodecs.imwrite(output, dst);

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}

	}

}