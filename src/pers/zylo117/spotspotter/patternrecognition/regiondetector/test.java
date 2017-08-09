package pers.zylo117.spotspotter.patternrecognition.regiondetector;

public class test {

	// Pythagoras-G
	// 推荐参数
	//

//	public static void Pythagoras_G(String input, String output) {
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		String a = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\1.jpg";
		String b = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\output1.jpg";
		// Upper-Left corner
		DetectCorners.corners(a, 206, 178, 20, 20, 15.0, b);	
		// Upper-Right corner
		DetectCorners.corners(a, 306, 178, 20, 20, 1.0, b);
		// Lower-Left corner
		DetectCorners.corners(a, 206, 326, 20, 20, 15.0, b);
		// Lower-Right corner
		DetectCorners.corners(a, 306, 326, 20, 20, 1.0, b);
		
		

	}
}
