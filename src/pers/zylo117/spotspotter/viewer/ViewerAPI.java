package pers.zylo117.spotspotter.viewer;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ViewerAPI {
	public static void viewer(String input, String windowname, int holdtime) {
		Mat mat =  Imgcodecs.imread(input);
		ImageShow iS = new ImageShow(mat, windowname);
		iS.imshow();
		ImageShow.waitKey(holdtime);
		}
}
