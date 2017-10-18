package pers.zylo117.spotspotter.toolbox;

import java.util.Collections;
import java.util.List;

public class GetMaxMinMidAvg {

	public static double getMaxFromList(List list) {
		final double max = (double) Collections.max(list);
		return max;
	}

	public static double getMinFromList(List list) {
		final double min = (double) Collections.min(list);
		return min;
	}

	public static double getMaxFromArray(double[] arr) {
		double max = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		return max;
	}

	public static double getMinFromArray(double[] arr) {
		double min = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] < min) {
				min = arr[i];
			}
		}
		return min;
	}

	public static double getMidFromArray(double[] arr) {
		final double max = getMaxFromArray(arr);
		final double min = getMinFromArray(arr);
		final double mid = (max + min) / 2;
		return mid;
	}

	public static double getMedianFromArray(List<Integer> arr) {
		Collections.sort(arr);
		final int length = arr.size();
		if (length % 2 == 0)
			return (arr.get(arr.size() / 2 - 1) + arr.get(arr.size())) / 2;
		else
			return arr.get(arr.size());
	}

	public static double getAvgFromArray(double[] arr) {
		final int length = arr.length;
		double sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
		}
		final double avg = sum / length;
		return avg;
	}

}
