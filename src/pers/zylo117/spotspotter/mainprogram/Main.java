package pers.zylo117.spotspotter.mainprogram;

import java.util.List;
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

	// ͼƬIO·��ȥPathManagement��java�޸�
	// ʶ�������ȥParameterManagement��java�޸�
	public static int algoIndex;

	public static void main(String[] args) throws Exception {
		Scanner inputKey = new Scanner(System.in);

		// ѯ���㷨
		System.out.println("Welcome running Classified Project Argus");
		System.out.println("Press Enter to Go Default");
		System.out.println("Press 1 to run Plato_G");
		System.out.println("Press 2 to run Pythagoras_G");

		int algoIndex = inputKey.nextInt();

		// ���幤��·��
		PathManagement.definePath();

		// ����������ͳ�ʼ�����ش���
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat cover = Imgcodecs.imread(System.getProperty("user.dir")+"/cover.jpg");
		CentralControl.imshow(cover, "CentralCtrl");

		while (true) {
			Time.waitFor(100);
			if (CentralControl.hasWorkDir) {
				FileListener.Autoscript(Main.algoIndex);
			}
		}
		// System.exit(0);
	}
}
