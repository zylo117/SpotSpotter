package pers.zylo117.spotspotter.patternrecognition;

import org.opencv.core.Point;

public class RotationAngle {
	public static double slope(Point p1, Point p2) {
		double length = Math.pow((p2.x-p1.x), 2) + Math.pow((p2.y-p1.y), 2);
		double length_x = p2.x-p1.x;
		double cos = Math.abs(length_x)/length;
		double angle = Math.acos(cos);
		return angle;
		
	}
}
