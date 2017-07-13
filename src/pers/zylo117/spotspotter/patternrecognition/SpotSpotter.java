package pers.zylo117.spotspotter.patternrecognition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageReader;

import pers.zylo117.spotspotter.toolbox.File2ImageStream;
import pers.zylo117.spotspotter.toolbox.GetMaxMin;
import pers.zylo117.spotspotter.toolbox.GetPostfixReader;

public class SpotSpotter {

	public static double resultC;

	// colorvalue 为10进制，0~255的数
	public static void marking(String input, int x, int y, int matrixsize, double theshold) throws IOException {

		long beginTime = new Date().getTime();

		// 读取图片格式
		File file = new File(input);
		String formatname = GetPostfixReader.getPostfix(file);

		// 图片读入成流
		ImageReader reader = File2ImageStream.F2IS(input, formatname);
		int width = (int) Math.floor(reader.getWidth(0));
		int height = (int) Math.floor(reader.getHeight(0));

		matrixsize = 1;

		int spottedSpot = 0;
		// 主算法，除了四周边缘像素，所有像素的值A，减去周围8个值(去除最大最小后的6个）得平均值B，A-B的绝对值，除以B，得到C,一个0~正无穷的差异值
		for (int i = 1; i < width - 1; i += matrixsize) {
			for (int j = 1; j < height - 1; j += matrixsize) {
				double a1 = (double) GetPixelArray.data[i - 1][j - 1];
				double a2 = (double) GetPixelArray.data[i - 1][j];
				double a3 = (double) GetPixelArray.data[i - 1][j + 1];
				double a4 = (double) GetPixelArray.data[i][j - 1];
				double a5 = (double) GetPixelArray.data[i][j + 1];
				double a6 = (double) GetPixelArray.data[i + 1][j - 1];
				double a7 = (double) GetPixelArray.data[i + 1][j];
				double a8 = (double) GetPixelArray.data[i + 1][j + 1];

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
				resultC = (GetPixelArray.data[i][j] - avgB) / avgB;

				if (resultC > theshold) {
					spottedSpot++;
					System.out.println("X: " + i + "\tY: " + j + "\tDifference " + resultC * 100 + "%");
				}
			}
		}
		System.out.println("Total Spot = " + spottedSpot);

		long endTime = new Date().getTime();
		System.out.println("Spotting Tact Time：[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}
}
