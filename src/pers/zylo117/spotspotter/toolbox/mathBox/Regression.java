package pers.zylo117.spotspotter.toolbox.mathBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Point;

public class Regression {
	public static Line lineFromMapList(List<Map<Point, Double>> maplist) {
		final List<Point> list = new ArrayList<>();
		for (int i = 0; i < maplist.size(); i++) {
			Map dataMap = new HashMap<>();
			dataMap = maplist.get(i);

			final Set<Map.Entry<Point, Double>> entryseSet = dataMap.entrySet();
			for (final Map.Entry<Point, Double> entry : entryseSet) {
				final Point p = entry.getKey();
				list.add(p);
			}
		}
		
		// 先求x,y的平均值x_avg, y_avg
		// 再用公式代入求解：k=(x1y1+x2y2+...xnyn-nXY)/(x1^2+x2^2+...xn^2-nX^2)
		// 后把x,y的平均数X，Y代入b=Y-kX
		// 求出b并代入总的公式y=kx+b得到线性回归方程
		// （X为xi的平均数，Y为yi的平均数）

		// x 均值
		double x_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			x_avg += list.get(i).x;
		}
		x_avg = x_avg / list.size();

		// y 均值
		double y_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			y_avg += list.get(i).y;
		}
		y_avg = y_avg / list.size();

		// 求斜率k
		// 求分子
		double numerator = 0;
		for (int i = 0; i < list.size(); i++) {
			numerator += list.get(i).x * list.get(i).y;
		}
		numerator -= list.size() * x_avg * y_avg;
		// 求分母
		double denominator = 0;
		for (int i = 0; i < list.size(); i++) {
			denominator += Math.pow(list.get(i).x, 2);
		}
		denominator -= list.size() * Math.pow(x_avg, 2);
		final double k = numerator / denominator;

		if (denominator == 0) {
			final Line regressionLine = new Line(new Point(x_avg, 0), new Point(x_avg, 1));
			return regressionLine;
		} else {
			// 求b
			final double b = y_avg - k * x_avg;
			final Line regressionLine = new Line(k, b);
			return regressionLine;
		}
	}
	
	public static Line lineFromPointList(List<Point> list) {		
		// 先求x,y的平均值x_avg, y_avg
		// 再用公式代入求解：k=(x1y1+x2y2+...xnyn-nXY)/(x1^2+x2^2+...xn^2-nX^2)
		// 后把x,y的平均数X，Y代入b=Y-kX
		// 求出b并代入总的公式y=kx+b得到线性回归方程
		// （X为xi的平均数，Y为yi的平均数）

		// x 均值
		double x_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			x_avg += list.get(i).x;
		}
		x_avg = x_avg / list.size();

		// y 均值
		double y_avg = 0;
		for (int i = 0; i < list.size(); i++) {
			y_avg += list.get(i).y;
		}
		y_avg = y_avg / list.size();

		// 求斜率k
		// 求分子
		double numerator = 0;
		for (int i = 0; i < list.size(); i++) {
			numerator += list.get(i).x * list.get(i).y;
		}
		numerator -= list.size() * x_avg * y_avg;
		// 求分母
		double denominator = 0;
		for (int i = 0; i < list.size(); i++) {
			denominator += Math.pow(list.get(i).x, 2);
		}
		denominator -= list.size() * Math.pow(x_avg, 2);
		final double k = numerator / denominator;

		if (denominator == 0) {
			final Line regressionLine = new Line(new Point(x_avg, 0), new Point(x_avg, 1));
			return regressionLine;
		} else {
			// 求b
			final double b = y_avg - k * x_avg;
			final Line regressionLine = new Line(k, b);
			return regressionLine;
		}
	}
}
