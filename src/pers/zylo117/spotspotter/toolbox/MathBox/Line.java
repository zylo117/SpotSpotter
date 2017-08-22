package pers.zylo117.spotspotter.toolbox.mathBox;

import org.opencv.core.Point;

public class Line {
	public double A, B, C, D, k, a, b;

	public Point p1, p2;

//	public Line(double A, double B, double C) {
//		this.A = A;
//		this.B = B;
//		this.C = C;
//
//	}

	public Line(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.k = (p1.y - p2.y) / (p1.x - p2.x);
		this.b = p1.y - k * p1.x;
		
		this.A = k;
		this.B = -1;
		this.C = b;
	}

	public Line() {
	    this(new Point(), new Point());
	}
}
