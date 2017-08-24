package pers.zylo117.pictureprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import pers.zylo117.patternrecognition.GetPixelArray;
import pers.zylo117.toolbox.Mat2BufferedImage;

public class Picture {

	public BufferedImage bimg;
	public int[][] dataSingleChannel;
	public int[][] data;
	public static Point ulP, urP, llP, lrP;
	public int width, height;

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
}
