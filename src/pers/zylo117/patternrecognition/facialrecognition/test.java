package pers.zylo117.patternrecognition.facialrecognition;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.patternrecognition.regiondetector.CornerDetector;

public class test {

public static void main(String[] args) {
	String input = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\face.jpg";
	String output = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\faceoutput.jpg";

StaticFacialRecognition.staticFace(input, output);
CameraFacialRecognition.CamFaceDetector();

}
}