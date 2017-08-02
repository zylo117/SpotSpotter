package pers.zylo117.spotspotter.pictureprocess;

public class Parameter {

	public static int ROIstart_x;
	public static int ROIstart_y;
	public static int ROIWidth;
	public static int ROIHeight;
	public static double theshold;

	public static void getParameter(String input) {

		switch (TargetClassifier.getClass(input)) {

		case "GA":
			// GA Spot识别参数
			// 输入ROI起始x/y,ROI画像长宽，阀值大小
			ROIstart_x = 394;
			ROIstart_y = 338;
			ROIWidth = 560;
			ROIHeight = 320;
			theshold = 0.07;
			break;

		case "AA":
			// Lumber AA Glue识别参数
			// 输入ROI起始x/y,ROI画像长宽，阀值大小
			// ROIstart_x = 186;
			// ROIstart_y = 166;
			// ROIWidth = 149;
			// ROIHeight = 184;
			// theshold = 0.25;
			ROIstart_x = 207;
			ROIstart_y = 186;
			ROIWidth = 101;
			ROIHeight = 146;
			theshold = 0.40;
			break;

		}

	}

}
