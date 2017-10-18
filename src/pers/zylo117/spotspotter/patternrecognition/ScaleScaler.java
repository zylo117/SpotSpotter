package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageReader;

import pers.zylo117.spotspotter.toolbox.File2ImageReader;
import pers.zylo117.spotspotter.toolbox.GetPostfix;

public class ScaleScaler {
	public static BufferedImage mosaic(String input, int width, int height) throws IOException {

		// 读取图片格式
		final String formatname = GetPostfix.fromFilepath(input);

		// 图片读入成流
		final ImageReader reader = File2ImageReader.F2IR(input);
		final int oriwidth = reader.getWidth(0);
		final int oriheight = reader.getHeight(0);

		return null;

	}
}