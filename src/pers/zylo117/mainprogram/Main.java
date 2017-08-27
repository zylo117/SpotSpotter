package pers.zylo117.mainprogram;

import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.fileprocessor.FileListener;
import pers.zylo117.patternrecognition.Comparison;
import pers.zylo117.patternrecognition.ROI_Irregular;
import pers.zylo117.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.patternrecognition.facialrecognition.CameraFacialRecognition;
import pers.zylo117.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.pictureprocess.Picture;
import pers.zylo117.pictureprocess.drawer.DrawPoint;
import pers.zylo117.toolbox.Timer;
import pers.zylo117.viewer.CentralControl;
import pers.zylo117.viewer.MatView;

public class Main {

	// 图片IO路径去PathManagement。java修改
	// 识别参数请去ParameterManagement。java修改
	public static int algoIndex;

	public static void main(String[] args) throws Exception {
		Scanner inputKey = new Scanner(System.in);

		// 询问算法
		System.out.println("Welcome running Classified Project Argus");
		System.out.println("Press Enter to Go Default");
		System.out.println("Press 1 to run Plato_G");
		System.out.println("Press 2 to run Pythagoras_G");

		int algoIndex = inputKey.nextInt();

		PathManagement.definePath();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat cover = Imgcodecs.imread(Main.class.getResource("../../../cover.jpg").getPath().substring(1));
		CentralControl.imshow(cover, "CentralCtrl");

		while (true) {
			Timer.waitFor(100);
			if (CentralControl.hasWorkDir) {
				FileListener.Autoscript(Main.algoIndex);
			}
		}
		// AlgoList.pythagoras_G();

		// System.exit(0);
	}
}
