package pers.zylo117.spotspotter.pictureprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class Picture {

	public BufferedImage bimg;
	public int[][] dataSingleChannel;
	public int[][] data;
	public static Point ulP, urP, llP, lrP;
	public static String fileName;
	public static String filePath;
	public static String fileParent;
	public int width, height;
	public static String processName;
	public static String productName;
	public static String MachineName;
	public static String material;

	public static List<Map<Point, Double>> failureData;

	public Picture(String file) throws IOException {
		this.bimg = ImageIO.read(new File(file));
		this.data = GetPixelArray.pixelArray(bimg, false);
		this.dataSingleChannel = GetPixelArray.pixelArray(bimg, true);
		this.width = bimg.getWidth();
		this.height = bimg.getHeight();
	}

	public Picture(Mat matInput) {
		this.bimg = Mat2BufferedImage.mat2BI(matInput);
		this.data = GetPixelArray.pixelArray(bimg, false);
		this.dataSingleChannel = GetPixelArray.pixelArray(bimg, true);
		this.width = matInput.width();
		this.height = matInput.height();
	}

	public static String result() {
		if (failureData.isEmpty()) {
			return "OK";
		} else
			return "NG";
	}

	public static String postFixWithDot() {
		final String postFixWithDot = fileName.substring(fileName.lastIndexOf("."));
		return postFixWithDot;
	}

	public static String fileNameWOPostfix() {
		final String fileNameWOPostfix = fileName.replace(postFixWithDot(), "");
		return fileNameWOPostfix;
	}
}
