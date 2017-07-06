package pers.zylo117.spotspotter.patternrecognition;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import pers.zylo117.spotspotter.fileprocessor.PostfixReader;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.pictureprocess.Image2BufferedImage;

public class GetColorValue {

	public static int[][] data;
	// 分离RGB变量,非必要,0=Red,1=Green,2=Blue,8bit灰阶图片RGB相等
	public static int[] rgb = new int [3];
	public static int[][] rawoutputR;
	public static int[][] rawoutputG;
	public static int[][] rawoutputB;
	public static int[][] rawoutputRGB;
	public static int ROIstart_x;
	public static int ROIstart_y;
	public static int ROIlength_x;
	public static int ROIlength_y;
	public static String formatname;
	public static ImageOutputStream rawoutputstream;

	public static void getData(String inputpath, String outputpath,int time, int ROIstart_x, int ROIstart_y, int ROIlength_x, int ROIlength_y) {
		GetColorValue.ROIstart_x = ROIstart_x;
		GetColorValue.ROIstart_y = ROIstart_y;
		GetColorValue.ROIlength_x = ROIlength_x;
		GetColorValue.ROIlength_y = ROIlength_y;
		
		//延时缓冲
		try{  
			Thread.currentThread();
			Thread.sleep(time);//毫秒 ms  
		}   
		catch(Exception e){
			e.printStackTrace();
		} 
		
		File file = new File(inputpath);
		BufferedImage bimg;
		
		try {
			// 定义图片流所需变量
//			Iterator<ImageReader> readers;
//			ImageReader reader;
//			ImageInputStream iis;

			// 读取图片格式
			formatname = PostfixReader.getPostfix(file);
			
			//读取图片流
			//由于imageIO读取某些格式的图片的ICC信息时错误，所以改类格式的图片要用原始方法读取
//			if (formatname.equals("jpg")) {
				Image pic = Toolkit.getDefaultToolkit().getImage(file.getPath());
				bimg = Image2BufferedImage.toBufferedImage(pic);
//			} 
//			else {
//				readers = ImageIO.getImageReadersByFormatName(formatname);
//				reader = (ImageReader) readers.next();
//				iis = ImageIO.createImageInputStream(file);
//				reader.setInput(iis, false);
//				int imageindex = 0;
//				bimg = reader.read(imageindex);
//				reader.dispose();
//				imageindex++;
//			}
			
			//像素矩阵主算法
			data = new int[ROIlength_x][ROIlength_y];
			rawoutputR = new int[ROIlength_x][ROIlength_y];
			rawoutputG = new int[ROIlength_x][ROIlength_y];
			rawoutputB = new int[ROIlength_x][ROIlength_y];
			rawoutputRGB = new int[ROIlength_x][ROIlength_y];
			// 方式一：通过getRGB()方式获得像素矩阵
			// 此方式为沿Height方向扫描
			for (int i = 0; i < ROIlength_x; i++) {
				for (int j = 0; j < ROIlength_y; j++) {
					data[i][j] = bimg.getRGB(i + ROIstart_x, j + ROIstart_y);

					rgb[0] = (data[i][j] & 0xff0000);
					rgb[1] = (data[i][j] & 0xff00);
					rgb[2] = (data[i][j] & 0xff);

					// 求测试图和完美对比图的差值
					rawoutputR[i][j] = rgb[0] - Comparison.perfectdataR[i][j];
					rawoutputG[i][j] = rgb[1] - Comparison.perfectdataG[i][j];
					rawoutputB[i][j] = rgb[2] - Comparison.perfectdataB[i][j];
					rawoutputRGB[i][j] = rawoutputR[i][j] + rawoutputG[i][j] + rawoutputB[i][j];

					// 输出一列数据比对
					if (i == 0) {
						System.out.printf("%x\t", data[i][j]);
						// System.out.printf("%x\t", rgb[0]);
						// System.out.printf("%x\t", rgb[1]);
						// System.out.printf("%x\t", rgb[2]);
						// System.out.printf("ff%x\t", rgb[0] + rgb[1] + rgb[2]);
						System.out.printf("%x\t", rawoutputRGB[i][j]);
					}
				}
			}
			bimg.flush();
			System.out.println("");
			System.out.println("Input Image Reading Complete");

			// 写入上述ARGB信息到原始缓存图像，并写入到原始图像路径
			BufferedImage rawbimg;
			if (formatname.equals("png")) {
				rawbimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_ARGB);
			}
			else {
			rawbimg = new BufferedImage(ROIlength_x, ROIlength_y, BufferedImage.TYPE_INT_RGB);
			}
			
			for (int i = 0; i < ROIlength_x; i++) {
				for (int j = 0; j < ROIlength_y; j++) {
					rawbimg.setRGB(i, j, rawoutputRGB[i][j]);
				}
			}
			FileOutputStream fos = new FileOutputStream(outputpath);
			ImageIO.write(rawbimg, formatname, fos);
			System.out.println("Raw Image Output Complete");
			System.out.println("");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}