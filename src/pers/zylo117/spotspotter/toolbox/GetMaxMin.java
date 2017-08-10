package pers.zylo117.spotspotter.toolbox;

import java.util.Collections;
import java.util.List;

public class GetMaxMin {

	public static double getMaxFromList(List list) {
		double max = (double) Collections.max(list);
		return max;
	}

	public static double getMinFromList(List list) {
		double min = (double) Collections.min(list);
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
}
