package pictureProcess;

import java.util.Scanner;

import fileListener.FileListener;
import patternRecognition.ColorValue;
import patternRecognition.Comparison;
import pictureProcess.PicProcess;

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

		//计算完美对比图的像素矩阵
		Comparison.getperfectData(PicProcess.perfect);
		System.out.println("Perfect Pixel Array Created");
		
		
		// Autoscript,自动监控文件创建,裁剪至整个滤光片(无白边),计算待计算图的像素矩阵
		FileListener.Autoscript();
		System.exit(0);
	}
}
