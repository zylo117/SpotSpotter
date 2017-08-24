package pers.zylo117.toolbox.mathBox;

import java.math.BigDecimal;

import org.opencv.core.Point;

public class MathBox {

	public static double slopeAngle(Point p1, Point p2) {
		double length = Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
		double length_x = p2.x - p1.x;
		double cos = length_x / length;
		double angle = Math.acos(cos);
		return angle;
	}

	public static double lineDistance(Line l1, Line l2) {
		double k1 = l1.k;
		double b1 = l1.b;
		double k2 = l2.k;
		double b2 = l2.b;
		double length;

		if (k1 == Double.POSITIVE_INFINITY || k1 == Double.NEGATIVE_INFINITY) {
			length = Math.abs(l1.p1.x - l2.p1.x);
			return length;
		} else if (k1 == k2) {
			double delta_intercept = Math.abs(b1 - b2);
			double l = Math.sqrt(Math.pow(k1, 2) + 1);
			length = delta_intercept / l;
			return length;
		} else {
			System.out.println("These 2 lines aren't parallel");
			return 0;
		}
	}

	// 以Y轴截距增加为正，向Y轴截距减少为负
	public static Line lineOffset(Line inputLine, double offset, boolean ifPositiveOffset) {
		double k = inputLine.k;
		Line outputLine = new Line();
		if (k == Double.POSITIVE_INFINITY || k == Double.NEGATIVE_INFINITY) {
			Point newP1 = new Point(inputLine.p1.x + offset, inputLine.p1.y);
			Point newP2 = new Point(inputLine.p2.x + offset, inputLine.p2.y);
			outputLine = new Line(newP1, newP2);
			return outputLine;
		} else {
			double l = Math.sqrt(Math.pow(k, 2) + 1) * offset;
			double newb;
			if (ifPositiveOffset) {
				newb = inputLine.b + l;
			} else
				newb = inputLine.b - l;
			Point newP1 = new Point(0, newb);
			Point newP2 = new Point(0, -newb / k);
			outputLine = new Line(newP1, newP2);
			return outputLine;
		}
	}

	public static double pointDistance(Point p1, Point p2) {
		double d = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
		return d;
	}

	public static Point rotateAroundAPoint(Point cc, Point target, double angleInRadian) {
		double cos = Math.cos(angleInRadian);
		double sin = Math.sin(angleInRadian);
		double x = (target.x - cc.x) * cos - (target.y - cc.y) * sin + cc.x;
		double y = (target.x - cc.x) * sin + (target.y - cc.y) * cos + cc.y;
		BigDecimal bd_x = new BigDecimal(x);
		x = bd_x.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		BigDecimal bd_y = new BigDecimal(y);
		y = bd_y.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
		Point newPoint = new Point(x, y);
		return newPoint;
	}
}