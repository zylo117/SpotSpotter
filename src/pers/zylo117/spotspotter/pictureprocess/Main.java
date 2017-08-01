package pers.zylo117.spotspotter.pictureprocess;

import java.util.Scanner;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.pictureprocess.PathManagement;

public class Main {

	// 图片IO路径去PathManagement。java修改
	// 识别参数请去ParameterManagement。java修改

public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		// 询问算法
		System.out.println("Welcome running Classified Project Argus");
		System.out.println("Press Enter to Go Default");
		String answer = input.nextLine();

		if (answer.equals("1") || answer.equals("1")) {
		}

		FileListener.Autoscript(2);

		System.exit(0);
	}
}
