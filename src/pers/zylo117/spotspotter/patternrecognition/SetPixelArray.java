package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;

public class SetPixelArray {
	public static BufferedImage fromPixelArray(int[][] data, int width, int height) {
		final BufferedImage bibimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bibimg.setRGB(i, j, data[i][j]);
			}
		}
		return bibimg;
	}

	public static BufferedImage fromRaster(byte[] data, int width, int height) {
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		image.getRaster().setDataElements(0, 0, width, height, data);
		return image;
	}
}
