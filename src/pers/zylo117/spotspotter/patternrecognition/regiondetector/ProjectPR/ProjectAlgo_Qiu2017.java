package pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.SetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ROIOutput;
import pers.zylo117.spotspotter.toolbox.BufferedImage2Mat;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;
import pers.zylo117.spotspotter.viewer.MatView;

// Enlightened by YuNing.Qiu, Postgraduate of GDUT, a great brother
public class ProjectAlgo_Qiu2017 {
	public static int[][] colorTrend(Mat input) {
		BufferedImage bimg = Mat2BufferedImage.mat2BI(input);
		int[][] singleChannelData = GetPixelArray.pixelArray(bimg, false);

		for (int i = 0; i < input.width(); i++) {
			System.out.printf("%x\t", singleChannelData[i][50]);
		}
		return singleChannelData;
	}
	
	public static void main(String[] args) {
		String input = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/7.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output7.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat imgOrigin = ROIOutput.Pythagoras_G(input);
		MatView.imshow(imgOrigin, "Original Image");
		int[][] data = colorTrend(imgOrigin);
		BufferedImage bimg = SetPixelArray.fromPixelArray(data, imgOrigin.width(), imgOrigin.height());
		Mat out = BufferedImage2Mat.img2Mat(bimg);
		MatView.imshow(out, "out");
	}

}
