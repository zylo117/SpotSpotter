package pers.zylo117.spotspotter.mainprogram;

import pers.zylo117.spotspotter.pictureprocess.Picture;

public class TargetClassifier {
	public static String getProcessNameFromPath(String input) {
		String processName;
		switch (GetPicType.getPicTypeFromPath(input)) {

		case "glue":
			processName = "AA";
			break;

		case "chip":
			processName = "GA";
			break;

		// case "glass":
		// processName = "GA";
		// break;

		default:
			processName = "Unknown";
			break;
		}
		return processName;
	}

	public static String getProcessNameFromFilename(String filename) {
		String processName;
		switch (GetPicType.getPicTypeFromPath(filename)) {

		case "glue":
			processName = "AA";
			break;

		case "chip":
			processName = "GA";
			break;

		// case "glass":
		// processName = "GA";
		// break;

		default:
			processName = "Unknown";
			break;
		}
		return processName;
	}

	public static String getProcessNameFromPic(Picture pic) {
		String processName;
		switch (GetPicType.getPicTypeFromPic(pic)) {

		case "glue":
			processName = "AA";
			break;

		case "Chip":
			processName = "GA";
			break;

		// case "glass":
		// processName = "GA";
		// break;

		default:
			processName = "Unknown";
			break;
		}
		return processName;
	}
}
