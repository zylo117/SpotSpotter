package pers.zylo117.spotspotter.pictureprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class Picture {

	public BufferedImage bimg;
	public int[][] dataSingelChannel;
	public int[][] data;
	public static Point ulP,urP,llP,lrP;
		
	public Picture(String file) throws IOException {
		this.bimg = ImageIO.read(new File(file));
		this.data = GetPixelArray.pixelArray(bimg, false);
		this.dataSingelChannel = GetPixelArray.pixelArray(bimg, true);
	}
	
	public Picture(Mat matInput) {
		this.bimg = Mat2BufferedImage.mat2BI(matInput);
		this.data = GetPixelArray.pixelArray(bimg, false);
		this.dataSingelChannel = GetPixelArray.pixelArray(bimg, true);
	}
}
