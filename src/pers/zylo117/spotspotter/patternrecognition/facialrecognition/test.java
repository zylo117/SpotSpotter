package pers.zylo117.spotspotter.patternrecognition.facialrecognition;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.patternrecognition.regiondetector.DetectCorners;

public class test {

public static void main(String[] args) {
	String input = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\face.jpg";
	String output = "D:\\workspace\\SpotSpotter\\src\\pers\\zylo117\\spotspotter\\image\\faceoutput.jpg";

StaticFacialRecognition.staticFace(input, output);
CameraFacialRecognition.CamFaceDetector();

}
}