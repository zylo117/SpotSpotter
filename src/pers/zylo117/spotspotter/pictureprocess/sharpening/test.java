package pers.zylo117.spotspotter.pictureprocess.sharpening;

public class test {
	public static void main(String[] args) {
		final String input = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\1.jpg";
		final String output = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\1clear.jpg";

		Laplacian.laplacian(input, output, 0);
	}
}
