package pers.zylo117.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.toolbox.BMPReader;
import pers.zylo117.toolbox.GetPostfix;
import pers.zylo117.toolbox.Timer;

public class GetPixelArray {

	public static int[][] data;
	// ����RGB����,�Ǳ�Ҫ,0=Red,1=Green,2=Blue,8bit�ҽ�ͼƬRGB���
	public static int[] rgb = new int[3];
	public static int[][] rawoutputR;
	public static int[][] rawoutputG;
	public static int[][] rawoutputB;
	public static int[][] rawoutputRGB;
	public static String formatname;
	public static ImageOutputStream rawoutputstream;
	public static int[][] colorvalue;
	// ����ͼƬ���������
	public static Iterator<ImageReader> readers;
	public static ImageReader reader;
	public static ImageInputStream iis;
	public static int ready2spot;

	public static void getData(String input, int time, int ROIstart_x, int ROIstart_y, int ROIlength_x,
			int ROIlength_y) {

		long beginTime = new Date().getTime();

		// ��ʱ����
		Timer.waitFor(time);

		File file = new File(input);

		// ��ȡͼƬ��ʽ
		formatname = GetPostfix.fromFilepath(input);

		BufferedImage bimg;
		if (formatname.equals("png")) {
			bimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_ARGB);
		} else {
			bimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_RGB);
		}

		int imageindex = 0;

		try {
			// ��ȡͼƬ��
			// ����imageIO��ȡĳЩ��ʽ��ͼƬ��ICC��Ϣʱ�������Ը����ʽ��ͼƬҪ��ԭʼ������ȡ
			while (true) {
				if (formatname.equals("jpg")) {
					// Image pic = Toolkit.getDefaultToolkit().getImage(file.getPath());
					// bimg = Image2BufferedImage.toBufferedImage(pic);
					readers = ImageIO.getImageReadersByFormatName(formatname);
					reader = readers.next();
					iis = ImageIO.createImageInputStream(file);
					reader.setInput(iis, false);
					bimg = reader.read(imageindex);
					reader.dispose();
					imageindex++;
					ready2spot = 1;

				}

				else if (formatname.equals("bmp")) {
					bimg = BMPReader.beBuffered(input);
					ready2spot = 1;
				}

				else {
					ready2spot = 0;
					System.out.println("Current Version only support JPEG");
					System.out.println("");
					break;
				}

				if (ready2spot == 1) {
					// ���ؾ������㷨
					data = new int[ROIlength_x][ROIlength_y];
					colorvalue = new int[ROIlength_x][ROIlength_y];

					// ��ʽһ��ͨ��getRGB()��ʽ������ؾ���
					// �˷�ʽΪ��Height����ɨ��
					for (int i = 0; i < ROIlength_x; i++) {
						for (int j = 0; j < ROIlength_y; j++) {
							data[i][j] = bimg.getRGB(i + ROIstart_x, j + ROIstart_y);

							// rgb[0] = (data[i][j] & 0xff0000);
							// rgb[1] = (data[i][j] & 0xff00);
							// rgb[2] = (data[i][j] & 0xff);

							// ���㵥ɫɫֵ
							colorvalue[i][j] = (data[i][j] & 0xff);
							// String tempst = Integer.toHexString(rawoutputRGB[i][j]);
							// String colorstr = tempst.substring(2, 4);
							// colorvalue[i][j] = Integer.valueOf(colorstr, 16);

							// ���һ�����ݱȶ�
							// if (i == 0) {
							// System.out.printf("%x\t", data[i][j]);
						}
					}
					bimg.flush();
					System.out.println("");
					System.out.println("Input Image Reading Complete");
					System.out.println("Raw Image Output Complete");

					long endTime = new Date().getTime();
					System.out.println("Rawimage output Tact Time:[" + (endTime - beginTime) + "]ms");
					System.out.println("");
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int[][] pixelArray(BufferedImage bimg, boolean ifGray) {
		data = new int[bimg.getWidth()][bimg.getHeight()];
		int[][] singleChannel = new int[bimg.getWidth()][bimg.getHeight()];
		// ��ʽһ��ͨ��getRGB()��ʽ������ؾ���
		// �˷�ʽΪ��Height����ɨ��
		for (int i = 0; i < bimg.getWidth(); i++) {
			for (int j = 0; j < bimg.getHeight(); j++) {
				data[i][j] = bimg.getRGB(i, j);

				// ���㵥ɫɫֵ
				singleChannel[i][j] = (data[i][j] & 0xff);
			}
		}

		if (!ifGray) {
			return data;
		} else {
			return singleChannel;
		}
	}
	
	public static double[][] pixelArray_Gray_Mat(Mat input) {
		Mat gray = new Mat();
		Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
		int cols = gray.cols();
		int rows = gray.rows();
		double[][] data = new double[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				double[] temp = new double[gray.channels()];
				temp = input.get(j, i);
				data[i][j] = temp[0];
			}
		}
		return data;
	}

}