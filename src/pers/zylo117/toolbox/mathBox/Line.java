package pers.zylo117.toolbox.mathBox;

import javax.security.auth.x500.X500Principal;

import org.opencv.core.Point;

public class Line {
	public double A, B, C, D, k, a, b;

	public Point p1, p2, anyP;

	public double anyX, anyY, slope;

	// public Line(double A, double B, double C) {
	// this.A = A;
	// this.B = B;
	// this.C = C;
	//
	// }

	public Line(Point p1, Point p2) {
		if (p1.x != p2.x) {
			this.p1 = p1;
			this.p2 = p2;
			this.k = (p1.y - p2.y) / (p1.x - p2.x);
			this.b = p1.y - k * p1.x;

			this.A = k;
			this.B = -1;
			this.C = b;
			this.slope = MathBox.slopeAngle(p1, p2);
		}

		else {
			this.k = Double.POSITIVE_INFINITY;
			this.b = Double.NEGATIVE_INFINITY;
			this.anyP = new Point(p1.x, 0);
			this.slope = Math.PI / 2;
		}
	}

	public Line(double k, double b) {
		this.k = k;
		this.b = b;
		this.p1 = new Point(0, b);
		this.p2 = new Point(-b / k, 0);
		this.slope = Math.atan(k);
	}

	public double solveX(double y) {
		if (k == Double.NEGATIVE_INFINITY || k == Double.POSITIVE_INFINITY) {
			return this.anyP.x;
		} else if (k != 0) {
			double x = (y - b) / k;
			return x;
		} else {
			return 0;
		}
	}

	public double solveY(double x) {
		if (k == Double.NEGATIVE_INFINITY || k == Double.POSITIVE_INFINITY) {
			return 0;
		} else if (k != 0) {
			double y = k * x + b;
			return y;
		} else {
			return b;
		}
	}

	public Line() {
		this(new Point(), new Point());
	}
}
