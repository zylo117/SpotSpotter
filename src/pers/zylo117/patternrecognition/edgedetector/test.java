package pers.zylo117.patternrecognition.edgedetector;

public class test {
	public static void main(String[] args) {
		String a = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\1.jpg";
		String b = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\output.jpg";
		CannyEdgeDetector.canny(a, b, 10);
	}
}
