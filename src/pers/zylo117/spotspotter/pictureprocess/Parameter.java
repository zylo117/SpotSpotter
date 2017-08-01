package pers.zylo117.spotspotter.pictureprocess;

public class Parameter {

	public static int ROIstart_x;
	public static int ROIstart_y;
	public static int ROIWidth;
	public static int ROIHeight;

	public static void getParameter(String input) {

		switch (TargetClassifier.getClass(input)) {

		case "GA":
			// GA Spot识别参数
			ROIstart_x = 394;
			ROIstart_y = 338;
			ROIWidth = 560;
			ROIHeight = 320;
			break;

		case "AA":
			// AA Glue识别参数
			// 输入ROI起始x/y,ROI画像长宽
			ROIstart_x = 186;
			ROIstart_y = 366;
			ROIWidth = 149;
			ROIHeight = 184;
			break;

		}

	}

}
