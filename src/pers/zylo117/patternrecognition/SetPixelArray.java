package pers.zylo117.patternrecognition;

import java.awt.image.BufferedImage;

public class SetPixelArray {
	public static BufferedImage fromPixelArray(int[][] data, int width, int height) {
		BufferedImage bibimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bibimg.setRGB(i, j, data[i][j]);
			}
		}
		return bibimg;
	}
}
