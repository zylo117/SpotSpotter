package pers.zylo117.spotspotter.toolbox.mathBox;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.opencv.core.Point;

public class Pointset {
	public static Point centerPoint(List<Point> list) {
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

		Point cP = new Point(x_avg, y_avg);
		return cP;
	}

	public static Point sigma(List<Point> list) {
		double sigma_x = 0;
		for (int i = 0; i < list.size(); i++) {
			sigma_x += Math.pow((list.get(i).x - centerPoint(list).x), 2);
		}
		sigma_x /= list.size();
		sigma_x = Math.sqrt(sigma_x);

		double sigma_y = 0;
		for (int i = 0; i < list.size(); i++) {
			sigma_y += Math.pow((list.get(i).y - centerPoint(list).y), 2);
		}
		sigma_y /= list.size();
		sigma_y = Math.sqrt(sigma_y);

		Point sigma = new Point(sigma_x, sigma_y);
		return sigma;
	}

	public static List<Point> confidenceIntervals(List<Point> list, double howManySigmas_bothSizes) {
		double singleSideSigma = howManySigmas_bothSizes / 2;
		double min_x = centerPoint(list).x - singleSideSigma * sigma(list).x;
		double min_y = centerPoint(list).y - singleSideSigma * sigma(list).y;
		double max_x = centerPoint(list).x + singleSideSigma * sigma(list).x;
		double max_y = centerPoint(list).y + singleSideSigma * sigma(list).y;

		// System.out.println(min_x+" "+min_y+" "+max_x+" "+max_y);

		List<Point> confiList = new ArrayList<Point>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).x > min_x && list.get(i).x < max_x && list.get(i).y > min_y && list.get(i).y < max_y) {
				confiList.add(new Point(list.get(i).x, list.get(i).y));
			}
		}
		return confiList;
	}

	public static List<Point> pointConnectivity(List<Point> list) {
		double distance;
		List<Point> newList = new ArrayList<Point>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				distance = MathBox.pointDistance(list.get(i), list.get(j));
//				System.out.println(distance);
				if (distance <= Math.sqrt(2) || distance >= 1) {
					newList.add(list.get(j));
				}
			}
		}
		return newList;
	}

	public static double[] fittingNormalDistribution(List<Point> list) {
		WeightedObservedPoints obs = new WeightedObservedPoints();
		for (int i = 0; i < list.size(); i++) {
			obs.add(list.get(i).x, list.get(i).y);
		}
		double[] result = GaussianCurveFitter.create().fit(obs.toList());
		return result;
		// result[0] = norm;
		// result[1] = mean;
		// result[2] = sigma;
	}
}
