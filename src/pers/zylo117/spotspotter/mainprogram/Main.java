package pers.zylo117.spotspotter.mainprogram;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Comparison;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.spotspotter.patternrecognition.facialrecognition.CameraFacialRecognition;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.pictureprocess.drawer.DrawPoint;
import pers.zylo117.spotspotter.toolbox.Time;
import pers.zylo117.spotspotter.viewer.CentralControl;
import pers.zylo117.spotspotter.viewer.MatView;

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

		Properties props = System.getProperties();
		String bits = String.valueOf(props.get("sun.arch.data.model"));
		System.out.println(bits + " bits System");
		if(Integer.parseInt(bits) == 64) {
			// 载入主封面和初始化主控窗口
			System.loadLibrary("opencv_java330_64");
		}
		else {
			System.loadLibrary("opencv_java330_86");
		}
		
		Mat cover = Imgcodecs.imread(System.getProperty("user.dir") + "/cover.jpg");

		MatView.imshow_reDraw(cover, "SpotMonitor");
		CentralControl.imshow(cover, "SpotSpotter");

		while (true) {
			Time.waitFor(100);
			if (CentralControl.hasWorkDir) {
				FileListener.autoDeepScan(Main.algoIndex);
			}
		}
		// System.exit(0);
	}
}
