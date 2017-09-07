package pers.zylo117.spotspotter.patternrecognition.corealgorithm;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.toolbox.BufferedImage2HQ_ImageFile;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.ImageStream2File;

public class SpotSpotter {

	public static double resultCenter;

	// colorvalue 为10进制，0~255的数
	public static void marking(String output, int width, int height, int matrixsize, double theshold)
			throws IOException {

		long beginTime = new Date().getTime();

		int spottedSpot = 0;

		// 主算法，除了四周边缘像素，所有像素的值A，减去周围8个值(去除最大最小后的6个）得平均值B，A-B的绝对值，除以B，得到C,一个0~正无穷的差异值
		// Center
		for (matrixsize = 1; matrixsize < 10; matrixsize++) {
			for (int i = matrixsize; i < width - matrixsize; i += matrixsize) {
				for (int j = matrixsize; j < height - matrixsize; j += matrixsize) {

					double a1 = GetPixelArray.colorvalue[i - 1][j - 1];
					double a2 = GetPixelArray.colorvalue[i - 1][j];
					double a3 = GetPixelArray.colorvalue[i - 1][j + 1];
					double a4 = GetPixelArray.colorvalue[i][j - 1];
					double a5 = GetPixelArray.colorvalue[i][j + 1];
					double a6 = GetPixelArray.colorvalue[i + 1][j - 1];
					double a7 = GetPixelArray.colorvalue[i + 1][j];
					double a8 = GetPixelArray.colorvalue[i + 1][j + 1];

					List<Double> list = new ArrayList<Double>();
					list.add(a1);
					list.add(a2);
					list.add(a3);
					list.add(a4);
					list.add(a5);
					list.add(a6);
					list.add(a7);
					list.add(a8);

					double maxB = GetMaxMinMidAvg.getMaxFromList(list);
					double minB = GetMaxMinMidAvg.getMinFromList(list);
					double avgB = (a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8 - maxB - minB) / 6;
					resultCenter = (GetPixelArray.colorvalue[i][j] - avgB) / avgB;

					if (resultCenter > theshold) {
						spottedSpot++;
						GetPixelArray.data[i][j] = 0xffff0000;

						System.out.println("Center" + "\tMatrix: " + matrixsize + "\tX: " + i + "\tY: " + j
								+ "\tDifference " + resultCenter * 100 + "%");
					}
				}
			}
		}

		// 写入上述ARGB信息到原始缓存图像，并写入到原始图像路径
		BufferedImage outputimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				outputimg.setRGB(i, j, GetPixelArray.data[i][j]);
			}
		}
		ImageStream2File.IS2F(outputimg, GetPixelArray.formatname, output);

		// 输出图片，质量0~1，0为最差，1为无损
		BufferedImage2HQ_ImageFile.writeHighQuality(outputimg, output, "jpg", 1);

		System.out.println("Total Spot = " + spottedSpot);

		long endTime = new Date().getTime();
		System.out.println("Spotting Tact Time:[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}

	public static List<Map<Point, Double>> spotList(Mat input, double thresh) {
		Picture pic = new Picture(input);
		double result;
		int spotQty = 0;
		List<Map<Point, Double>> spotList = new ArrayList<>();
		for (int i = 1; i < pic.width - 1; i += 1) {
			for (int j = 1; j < pic.height - 1; j += 1) {

				double a1 = pic.dataSingleChannel[i - 1][j - 1];
				double a2 = pic.dataSingleChannel[i - 1][j];
				double a3 = pic.dataSingleChannel[i - 1][j + 1];
				double a4 = pic.dataSingleChannel[i][j - 1];
				double a5 = pic.dataSingleChannel[i][j + 1];
				double a6 = pic.dataSingleChannel[i + 1][j - 1];
				double a7 = pic.dataSingleChannel[i + 1][j];
				double a8 = pic.dataSingleChannel[i + 1][j + 1];

				List<Double> list = new ArrayList<Double>();
				list.add(a1);
				list.add(a2);
				list.add(a3);
				list.add(a4);
				list.add(a5);
				list.add(a6);
				list.add(a7);
				list.add(a8);

				double maxB = GetMaxMinMidAvg.getMaxFromList(list);
				double minB = GetMaxMinMidAvg.getMinFromList(list);

				for (int k = 0; k < list.size(); k++) {
					if (list.get(k) == 0) {
						list.remove(k);
					}
				}

				double sum = 0;
				for (int l = 0; l < list.size(); l++) {
					sum += list.get(l);
				}
				
				double avgB = sum / list.size();
				result = Math.abs(pic.dataSingleChannel[i][j] - avgB) / 256;

				if (result > thresh) {
					spotQty++;
					Map datamap = new HashMap<>();
					datamap.put(new Point(i, j), result);
					spotList.add(datamap);

					System.out.println("Center" + "\tX: " + i + "\tY: " + j + "\tDifference " + result * 100 + "%");
				}
			}
		}
		System.out.println("Spot Quantity = " + spotQty);

		return spotList;
	}
}
