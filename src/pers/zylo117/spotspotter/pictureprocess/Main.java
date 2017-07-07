package pers.zylo117.spotspotter.pictureprocess;

import java.util.Scanner;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.pictureprocess.PicProcess;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		// 询问是否缩放
		System.out.println("Press E/e to Expert mode");
		System.out.println("Press Enter to Continue");
		String answer = input.nextLine();
		
		if (answer.equals("E") || answer.equals("e")) {
			System.out.println("Please input target pixel x");
			int x = input.nextInt();
			System.out.println("Please input target pixel y");
			int y = input.nextInt();
			System.out.println("Please input scale mode,0 or 1 or other positive number, recommended 1");
			System.out.println("0,force target pixel");
			System.out.println("1,keep aspect ratio, scaling with target pixel x");
			System.out.println("Others,keep aspect ratio, scaling up or down, with target ratio");
			double scale = 1;
			scale = input.nextDouble();
			System.out.println("Please input rotation angle, recommended 0°");
			double angle = 0;
			angle = input.nextDouble();
			PicProcess.zoom(x, y, scale, angle);
			System.out.println("PicZoom Finished");
		}
		
		// 图片IO路径去PicProcess。java修改
		
		//Version1 完美图对比法,±2颜色值偏差
		//计算完美对比图的像素矩阵
		Comparison.getperfectData(PicProcess.perfect);
		
		// Autoscript,自动监控文件创建,执行Job list
		FileListener.Autoscript();
		System.exit(0);
	}
}
