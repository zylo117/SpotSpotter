package pers.zylo117.spotspotter.mainprogram;

public class Parameter {

	public static int ROIstart_x;
	public static int ROIstart_y;
	public static int ROIWidth;
	public static int ROIHeight;
	public static double threshold;

	public static void getParameter(String input) {

		switch (TargetClassifier.getClass(input)) {

		case "GA":
			// GA Spotʶ�����
			// ����ROI��ʼx/y,ROI���񳤿���ֵ��С
			ROIstart_x = 394;
			ROIstart_y = 338;
			ROIWidth = 560;
			ROIHeight = 320;
			threshold = 0.07;
			break;
		case "AA":
			// Lumber AA Glueʶ�����
			// ����ROI��ʼx/y,ROI���񳤿���ֵ��С
			// ROIstart_x = 186;
			// ROIstart_y = 166;
			// ROIWidth = 149;
			// ROIHeight = 184;
			// theshold = 0.25;
			
			// Granite AA Glueʶ�����
			ROIstart_x = 207;
			ROIstart_y = 186;
			ROIWidth = 101;
			ROIHeight = 146;
			threshold = 0.40;
			break;

		}

	}

}
