package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Comparison {

	public static int[][] perfectdatargb;
	public static int[][] perfectdataR;
	public static int[][] perfectdataG;
	public static int[][] perfectdataB;
	// 分离RGB变量,非必要,0=Red,1=Green,2=Blue,8bit灰阶图片RGB相等
	public static int[] perfectrgb = new int[3];

	public static void getperfectData(String path) {
		try {
			BufferedImage perfectbimg = ImageIO.read(new File(path));
			perfectdatargb = new int[perfectbimg.getWidth()][perfectbimg.getHeight()];
			perfectdataR = new int[perfectbimg.getWidth()][perfectbimg.getHeight()];
			perfectdataG = new int[perfectbimg.getWidth()][perfectbimg.getHeight()];
			perfectdataB = new int[perfectbimg.getWidth()][perfectbimg.getHeight()];
			// 方式一：通过getRGB()方式获得像素矩阵
			// 此方式为沿Height方向扫描
			for (int i = 0; i < perfectbimg.getWidth(); i++) {
				for (int j = 0; j < perfectbimg.getHeight(); j++) {
					perfectdatargb[i][j] = perfectbimg.getRGB(i, j);
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
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}