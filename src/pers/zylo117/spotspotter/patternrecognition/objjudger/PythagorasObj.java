package pers.zylo117.spotspotter.patternrecognition.objjudger;

import java.util.List;

import org.opencv.core.Point;

public class PythagorasObj {
	public static boolean ifGlue(List<Point> list) {
		if(list.size()>3){
		}
		
		return true;
	}
	
	public static boolean ifDust(List<Point> list) {
		for (int i = 0; i < list.size(); i++) {
		}
		
		return true;
	}
}
