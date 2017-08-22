package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencv.core.Mat;

import pers.zylo117.spotspotter.toolbox.BufferedImage2HQ_ImageFile;
import pers.zylo117.spotspotter.toolbox.GetMaxMinMidAvg;
import pers.zylo117.spotspotter.toolbox.ImageStream2File;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class SpotSpotter {

	public static double resultCenter;

	// colorvalue Ϊ10���ƣ�0~255����
	public static void marking(String output, int width, int height, int matrixsize, double theshold)
			throws IOException {

		long beginTime = new Date().getTime();

		int spottedSpot = 0;

		// ���㷨���������ܱ�Ե���أ��������ص�ֵA����ȥ��Χ8��ֵ(ȥ�������С���6������ƽ��ֵB��A-B�ľ���ֵ������B���õ�C,һ��0~������Ĳ���ֵ
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

		// д������ARGB��Ϣ��ԭʼ����ͼ�񣬲�д�뵽ԭʼͼ��·��
		BufferedImage outputimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				outputimg.setRGB(i, j, GetPixelArray.data[i][j]);
			}
		}
		ImageStream2File.IS2F(outputimg, GetPixelArray.formatname, output);
		
		// ���ͼƬ������0~1��0Ϊ��1Ϊ����
		BufferedImage2HQ_ImageFile.writeHighQuality(outputimg, output, "jpg", 1);

		System.out.println("Total Spot = " + spottedSpot);

		long endTime = new Date().getTime();
		System.out.println("Spotting Tact Time:[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}

	public static Mat mat_version(Mat input, double thresh) {
		BufferedImage bimg = Mat2BufferedImage.mat2BI(input);
		
			for (int i = 1; i < bimg.getWidth() - 1; i += 1) {
				for (int j = 1; j < bimg.getHeight() - 1; j += 1) {

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
				}}
		return null;
	}
}
