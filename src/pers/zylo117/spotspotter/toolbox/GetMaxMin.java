package pers.zylo117.spotspotter.toolbox;

import java.util.Collections;
import java.util.List;

public class GetMaxMin {

	public static double getMax(List list) {
		double max = (double) Collections.max(list);
		return max;
	}

	public static double getMin(List list) {
		double min = (double) Collections.min(list);
		return min;
	}

	
}
