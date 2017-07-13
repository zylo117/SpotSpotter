package pers.zylo117.spotspotter.pictureprocess;

import java.util.Scanner;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.pictureprocess.PicProcess;

public class Main {
	
	// 图片IO路径去PicProcess。java修改
	
	// 输入ROI起始x/y,ROI画像长宽
	public static int ROIstart_x = 394;
	public static int ROIstart_y = 338;
	public static int ROIlength_x = 560;
	public static int ROIlength_y = 320;
	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		// 询问是否缩放
		System.out.println("Press 1 to Version 1 algorithm");
		System.out.println("Press Enter to Go Default");
		String answer = input.nextLine();
		

		
		if (answer.equals("1") || answer.equals("1")) {
			//Version1 完美图对比法,±2颜色值偏差
			//计算完美对比图的像素矩阵
			Comparison.getperfectData(PicProcess.perfect, ROIstart_x, ROIstart_y, ROIlength_x, ROIlength_y);
			// Autoscript,自动监控文件创建,执行图像对比算法
			FileListener.Autoscript(1);
		}

		FileListener.Autoscript(2);
		
		System.exit(0);
	}
}
