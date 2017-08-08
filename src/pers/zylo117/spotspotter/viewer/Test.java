package pers.zylo117.spotspotter.viewer;

import java.awt.Frame;

import org.opencv.core.Mat;

import pers.zylo117.spotspotter.toolbox.File2Matrix;

public class Test {
	public static void main(String[] args) {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/face.jpg";
		Mat mat =  File2Matrix.F2M(input);
		ImageShow iS = new ImageShow(mat, "fucker");
		iS.imshow();
		ImageShow.waitKey(0);
		}
}
