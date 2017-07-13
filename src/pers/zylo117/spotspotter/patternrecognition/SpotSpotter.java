package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageReader;

import pers.zylo117.spotspotter.toolbox.File2ImageStream;
import pers.zylo117.spotspotter.toolbox.GetMaxMin;
import pers.zylo117.spotspotter.toolbox.GetPostfixReader;
import pers.zylo117.spotspotter.toolbox.ImageReader2File;
import pers.zylo117.spotspotter.toolbox.ImageStream2File;

public class SpotSpotter {

	public static double resultCenter;

	// colorvalue 为10进制，0~255的数
	public static void marking(String input, String output, int x, int y, int matrixsize, double theshold) throws IOException {

		long beginTime = new Date().getTime();

		// 读取图片格式
		File file = new File(input);
		String formatname = GetPostfixReader.getPostfix(file);

		// 图片读入成流
		ImageReader reader = File2ImageStream.F2IS(input, formatname);
		int width = (int) Math.floor(reader.getWidth(0));
		int height = (int) Math.floor(reader.getHeight(0));

		int spottedSpot = 0;

		// 主算法，除了四周边缘像素，所有像素的值A，减去周围8个值(去除最大最小后的6个）得平均值B，A-B的绝对值，除以B，得到C,一个0~正无穷的差异值
		// Center
		for (matrixsize = 1; matrixsize < 10; matrixsize++) {
			for (int i = matrixsize; i < width - matrixsize; i += matrixsize) {
				for (int j = matrixsize; j < height - matrixsize; j += matrixsize) {

					double a1 = (double) GetPixelArray.colorvalue[i - 1][j - 1];
					double a2 = (double) GetPixelArray.colorvalue[i - 1][j];
					double a3 = (double) GetPixelArray.colorvalue[i - 1][j + 1];
					double a4 = (double) GetPixelArray.colorvalue[i][j - 1];
					double a5 = (double) GetPixelArray.colorvalue[i][j + 1];
					double a6 = (double) GetPixelArray.colorvalue[i + 1][j - 1];
					double a7 = (double) GetPixelArray.colorvalue[i + 1][j];
					double a8 = (double) GetPixelArray.colorvalue[i + 1][j + 1];

					List<Double> list = new ArrayList<Double>();
					list.add(a1);
					list.add(a2);
					list.add(a3);
					list.add(a4);
					list.add(a5);
					list.add(a6);
					list.add(a7);
					list.add(a8);

					double maxB = GetMaxMin.getMax(list);
					double minB = GetMaxMin.getMin(list);
					double avgB = (a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 - maxB - minB) / 6;
					resultCenter = (GetPixelArray.colorvalue[i][j] - avgB) / avgB;

					if (resultCenter > theshold) {
						spottedSpot++;
						GetPixelArray.data[i][j] = 0xffff0000;
						System.out.println("Center"+"\tMatrix: " + matrixsize + "\tX: " + i + "\tY: " + j + "\tDifference "
								+ resultCenter * 100 + "%");
					}
				}
			}
		}

		// 写入上述ARGB信息到原始缓存图像，并写入到原始图像路径
		BufferedImage rawbimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rawbimg.setRGB(i, j, GetPixelArray.data[i][j]);
			}
		}
		ImageStream2File.IS2F(rawbimg, formatname, output);

		System.out.println("Total Spot = " + spottedSpot);

		long endTime = new Date().getTime();
		System.out.println("Spotting Tact Time:[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}
}
