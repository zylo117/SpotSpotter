package pers.zylo117.spotspotter.mainprogram;

public class TargetClassifier {
	public static String getProcessName(String input) {
		String classname;
		switch (GetPicType.getPicType(input)) {
		
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
