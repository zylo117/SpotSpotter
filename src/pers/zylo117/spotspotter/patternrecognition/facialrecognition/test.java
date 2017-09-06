package pers.zylo117.spotspotter.patternrecognition.facialrecognition;

public class test {

public static void main(String[] args) {
	String input = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\face.jpg";
	String output = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\faceoutput.jpg";

StaticFacialRecognition.staticFace(input, output);
CameraFacialRecognition.CamFaceDetector();

}
}