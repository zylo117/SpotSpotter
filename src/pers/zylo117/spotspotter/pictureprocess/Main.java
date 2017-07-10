package pers.zylo117.spotspotter.pictureprocess;

import java.util.Scanner;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.pictureprocess.PicProcess;

public class Main {
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		// 询问是否缩放
		System.out.println("Press E/e to Expert mode");
		System.out.println("Press Enter to Continue");
		String answer = input.nextLine();
		
		if (answer.equals("E") || answer.equals("e")) {
			// 待开发
		}
		
		// 图片IO路径去PicProcess。java修改
		
		//Version1 完美图对比法,±2颜色值偏差
		//计算完美对比图的像素矩阵
		Comparison.getperfectData(PicProcess.perfect, 394, 338, 560, 320);
		
		// Autoscript,自动监控文件创建,执行Job list
		FileListener.Autoscript();
		System.exit(0);
	}
}
