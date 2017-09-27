package pers.zylo117.spotspotter.patternrecognition;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import pers.zylo117.spotspotter.toolbox.Image2BufferedImage;

public final class Comparison {

	public static int[][] perfectdatargb;
	public static int[][] perfectdataR;
	public static int[][] perfectdataG;
	public static int[][] perfectdataB;
	public static int ROIstart_x;
	public static int ROIstart_y;
	public static int ROIlength_x;
	public static int ROIlength_y;
	// 分离RGB变量,非必要,0=Red,1=Green,2=Blue,8bit灰阶图片RGB相等
	public static int[] perfectrgb = new int[3];

	public static void getperfectData(String path, int ROIstart_x, int ROIstart_y, int ROIlength_x, int ROIlength_y)
			throws IOException {
		
		Comparison.ROIstart_x = ROIstart_x;
		Comparison.ROIstart_y = ROIstart_y;
		Comparison.ROIlength_x = ROIlength_x;
		Comparison.ROIlength_y = ROIlength_y;
		
		final File file = new File(path);
		final Image pic = Toolkit.getDefaultToolkit().getImage(file.getPath());
		final BufferedImage perfectbimg = Image2BufferedImage.toBufferedImage(pic);
		perfectdatargb = new int[ROIlength_x][ROIlength_y];
		perfectdataR = new int[ROIlength_x][ROIlength_y];
		perfectdataG = new int[ROIlength_x][ROIlength_y];
		perfectdataB = new int[ROIlength_x][ROIlength_y];
		// 方式一：通过getRGB()方式获得像素矩阵
		// 此方式为沿Height方向扫描
		for (int i = 0; i < ROIlength_x; i++) {
			for (int j = 0; j < ROIlength_y; j++) {
				perfectdatargb[i][j] = perfectbimg.getRGB(i+ROIstart_x, j+ROIstart_y);
				perfectrgb[0] = (perfectdatargb[i][j] & 0xff0000);
				perfectrgb[1] = (perfectdatargb[i][j] & 0xff00);
				perfectrgb[2] = (perfectdatargb[i][j] & 0xff);

				// 把分量复制给对比图
				perfectdataR[i][j] = perfectrgb[0];
				perfectdataG[i][j] = perfectrgb[1];
				perfectdataB[i][j] = perfectrgb[2];

				// 输出一列数据比对
				if (i == 0)
					System.out.printf("%x\t", perfectdatargb[i][j]);
			}
		}
		System.out.println("");
		System.out.println("Perfect Pixel Array Created");
		System.out.println("");

	}

}