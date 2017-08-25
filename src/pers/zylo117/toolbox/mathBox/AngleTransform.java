package pers.zylo117.toolbox.mathBox;

public class AngleTransform {
	public static double degree2Radian(double angle) {
		double radian = angle / 180 * Math.PI;
		return radian;
	}

	public static double radian2Degree(double radian) {
		double angle;
		if (radian > 0) {
			while (radian > 2 * Math.PI) {
				radian -= 2 * Math.PI;
			}
			angle = radian / Math.PI * 180;
		} else {
			while (radian < -2 * Math.PI) {
				radian += 2 * Math.PI;
			}
			angle = radian / Math.PI * 180;
		}
		return angle;
	}
}
