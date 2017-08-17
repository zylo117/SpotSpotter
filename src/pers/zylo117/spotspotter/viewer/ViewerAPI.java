package pers.zylo117.spotspotter.viewer;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ViewerAPI {
	public static void viewer(String input, String windowname, int holdtime) {
		Mat mat =  Imgcodecs.imread(input);
		ImageGUI iS = new ImageGUI(mat, windowname);
		iS.imshow();
		ImageGUI.waitKey(holdtime);
		}
}
