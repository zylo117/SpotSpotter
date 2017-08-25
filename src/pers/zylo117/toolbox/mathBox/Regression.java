package pers.zylo117.toolbox.mathBox;

import java.util.List;

import org.opencv.core.Point;

public class Regression {
	public static Line line(List<Point> list) {

		// ����x,y��ƽ��ֵx_avg, y_avg
		// ���ù�ʽ������⣺k=(x1y1+x2y2+...xnyn-nXY)/(x1^2+x2^2+...xn^2-nX^2)
		// ���x,y��ƽ����X��Y����b=Y-kX
		// ���b�������ܵĹ�ʽy=kx+b�õ����Իع鷽��
		// ��XΪxi��ƽ������YΪyi��ƽ������

		// x ��ֵ
		double x_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			x_avg += list.get(i).x;
		}
		x_avg = x_avg / list.size();

		// y ��ֵ
		double y_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			y_avg += list.get(i).y;
		}
		y_avg = y_avg / list.size();

		// ��б��k
		// �����
		double numerator = 0;
		for (int i = 0; i < list.size(); i++) {
			numerator += list.get(i).x * list.get(i).y;
		}
		numerator -= list.size() * x_avg * y_avg;
		// ���ĸ
		double denominator = 0;
		for (int i = 0; i < list.size(); i++) {
			denominator += Math.pow(list.get(i).x, 2);
		}
		denominator -= list.size() * Math.pow(x_avg, 2);
		double k = numerator / denominator;

		if (denominator == 0) {
			Line regressionLine = new Line(new Point(x_avg, 0), new Point(x_avg, 1));
			return regressionLine;
		} else {
			// ��b
			double b = y_avg - k * x_avg;
			Line regressionLine = new Line(k, b);
			return regressionLine;
		}
	}
}
