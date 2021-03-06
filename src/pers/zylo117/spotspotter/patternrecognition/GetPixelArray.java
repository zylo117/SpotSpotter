package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.toolbox.BMPReader;
import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.Time;

public class GetPixelArray {

	public static int[][] data;
	// 分离RGB变量,非必要,0=Red,1=Green,2=Blue,8bit灰阶图片RGB相等
	public static int[] rgb = new int[3];
	public static int[][] rawoutputR;
	public static int[][] rawoutputG;
	public static int[][] rawoutputB;
	public static int[][] rawoutputRGB;
	public static String formatname;
	public static ImageOutputStream rawoutputstream;
	public static int[][] colorvalue;
	// 定义图片流所需变量
	public static Iterator<ImageReader> readers;
	public static ImageReader reader;
	public static ImageInputStream iis;
	public static int ready2spot;

	public static void getData(String input, int time, int ROIstart_x, int ROIstart_y, int ROIlength_x,
			int ROIlength_y) {

		final long beginTime = new Date().getTime();

		// 延时缓冲
		Time.waitFor(time);

		final File file = new File(input);

		// 读取图片格式
		formatname = GetPostfix.fromFilepath(input);

		BufferedImage bimg;
		if (formatname.equals("png")) {
			bimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_ARGB);
		} else {
			bimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_RGB);
		}

		int imageindex = 0;

		try {
			// 读取图片流
			// 由于imageIO读取某些格式的图片的ICC信息时错误，所以改类格式的图片要用原始方法读取
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
					// 像素矩阵主算法
					data = new int[ROIlength_x][ROIlength_y];
					colorvalue = new int[ROIlength_x][ROIlength_y];

					// 方式一：通过getRGB()方式获得像素矩阵
					// 此方式为沿Height方向扫描
					for (int i = 0; i < ROIlength_x; i++) {
						for (int j = 0; j < ROIlength_y; j++) {
							data[i][j] = bimg.getRGB(i + ROIstart_x, j + ROIstart_y);

							// rgb[0] = (data[i][j] & 0xff0000);
							// rgb[1] = (data[i][j] & 0xff00);
							// rgb[2] = (data[i][j] & 0xff);

							// 计算单色色值
							colorvalue[i][j] = (data[i][j] & 0xff);
							// String tempst = Integer.toHexString(rawoutputRGB[i][j]);
							// String colorstr = tempst.substring(2, 4);
							// colorvalue[i][j] = Integer.valueOf(colorstr, 16);

							// 输出一列数据比对
							// if (i == 0) {
							// System.out.printf("%x\t", data[i][j]);
						}
					}
					bimg.flush();
					System.out.println("");
					System.out.println("Input Image Reading Complete");
					System.out.println("Raw Image Output Complete");

					final long endTime = new Date().getTime();
					System.out.println("Rawimage output Tact Time:[" + (endTime - beginTime) + "]ms");
					System.out.println("");
					break;
				}

			}

		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	public static int[][] pixelArray(BufferedImage bimg, boolean ifGray) {
		data = new int[bimg.getWidth()][bimg.getHeight()];
		final int[][] singleChannel = new int[bimg.getWidth()][bimg.getHeight()];
		// 方式一：通过getRGB()方式获得像素矩阵
		// 此方式为沿Height方向扫描
		for (int i = 0; i < bimg.getWidth(); i++) {
			for (int j = 0; j < bimg.getHeight(); j++) {
				data[i][j] = bimg.getRGB(i, j);

				// 计算单色色值
				singleChannel[i][j] = (data[i][j] & 0xff);
			}
		}

		if (!ifGray) {
			return data;
		} else {
			return singleChannel;
		}
	}

	public static int[][] pixelArray_Gray_Mat(Mat input) {
		final Mat gray = new Mat();
		Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
		final int cols = gray.cols();
		final int rows = gray.rows();
		final int[][] data = new int[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				// double[] temp = new double[gray.channels()];
				// temp = input.get(j, i);
				data[i][j] = (int) input.get(j, i)[0];
			}
		}
		return data;
	}

	public static byte[] getRaster(Mat input) {
		final Mat gray = new Mat();
		Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
		final int cols = gray.cols();
		final int rows = gray.rows();
		final int elemSize = (int) gray.elemSize();
		final byte[] data = new byte[cols * rows * elemSize];
		gray.get(0, 0, data);

		return data;
	}

}