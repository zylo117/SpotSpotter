package pers.zylo117.spotspotter.patternrecognition.regiondetector;

import org.opencv.core.Point;

public class FourthCorner {

	public static Point fourthPoint(Point p1, Point p2, Point p3) {
		Point p4;
		// double diagonal;
		// diagonal = Math.sqrt(Math.pow((p3.x - p1.x), 2) + Math.pow((p3.y - p1.y), 2)
		// + Math.pow((p2.x - p1.x), 2)
		// + Math.pow((p2.y - p1.y), 2));
		//
		// double x21 = p2.x - p1.x;
		// double y21 = p2.y - p1.y;
		// double y31 = p3.y - p1.y;
		//
		// double a = Math.pow(y21, 2) + Math.pow(x21, 2);
		// double b = 2 * y21 * y31 * x21 - 2 * Math.pow(y21, 2) * p3.x - 2 *
		// Math.pow(x21, 2) * p1.x;
		// double c = -Math.pow(x21, 2) * Math.pow(diagonal, 2) + Math.pow(x21, 2) *
		// Math.pow(p1.x, 2)
		// + Math.pow(y21, 2) * Math.pow(p3.x, 2) + Math.pow(y31, 2) * Math.pow(x21, 2)
		// - 2 * y21 * y31 * x21 * p3.x;
		//
		// double delta = Math.pow(b, 2) - 4 * a * c;
		// double result_x1 = (-b+Math.sqrt(delta))/2*a;
		// double result_x2 = (-b-Math.sqrt(delta))/2*a;

		final double vector_x = p2.x - p1.x;
		final double vector_y = p2.y - p1.y;
		p4 = new Point(p3.x + vector_x, p3.y + vector_y);

		return p4;
	}
}
