package pers.zylo117.spotspotter.mainprogram;

import pers.zylo117.spotspotter.pictureprocess.Picture;

public class TargetClassifier {
	public static String getProcessNameFromPath(String input) {
		String classname;
		switch (GetPicType.getPicTypeFromPath(input)) {
		
		case "glue":
			classname = "AA";
			break;
			
		case "chip":
			classname = "GA";
			break;
			
		case "glass":
			classname = "GA";
			break;
			
		default:
			classname = "Unknown";
			break;
		}
		return classname;
	}
	
	public static String getProcessNameFromFilename(String filename) {
		String classname;
		switch (GetPicType.getPicTypeFromPath(filename)) {
		
		case "glue":
			classname = "AA";
			break;
			
		case "chip":
			classname = "GA";
			break;
			
		case "glass":
			classname = "GA";
			break;
			
		default:
			classname = "Unknown";
			break;
		}
		return classname;
	}
	
	public static String getProcessNameFromPic(Picture pic) {
		String classname;
		switch (GetPicType.getPicTypeFromPic(pic)) {
		
		case "glue":
			classname = "AA";
			break;
			
		case "chip":
			classname = "GA";
			break;
			
		case "glass":
			classname = "GA";
			break;
			
		default:
			classname = "Unknown";
			break;
		}
		return classname;
	}
}
