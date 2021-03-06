package pers.zylo117.spotspotter.toolbox;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Mat2BufferedImage {
	public static BufferedImage mat2BI(Mat matrix) {
		final int cols = matrix.cols();
		final int rows = matrix.rows();
		final int elemSize = (int) matrix.elemSize();
		final byte[] data = new byte[cols * rows * elemSize];
		int type;
		matrix.get(0, 0, data);
		switch (matrix.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for (int i = 0; i < data.length; i = i + 3) {
				b = data[i];
				data[i] = data[i + 2];
				data[i + 2] = b;
			}
			break;
		default:
			return null;
		}
		final BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0, 0, cols, rows, data);
		return image2;
	}

	public static void main(String[] args) {
		System.loadLibrary("opencv_java330_64");
		final Mat mat = Imgcodecs
				.imread("Z:\\2017\\09\\19\\20170919_090000514_ALL_T738154GR.01_M441F26952-1_Pkg1_Chip.jpg");
		final BufferedImage bImage = mat2BI(mat);
	}
}
