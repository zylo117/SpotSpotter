package pers.zylo117.spotspotter.mainprogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.fileprocessor.FIndexReader;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.gui.viewer.MatView;
import pers.zylo117.spotspotter.toolbox.Time;

public class Main {

	// ͼƬIO·��ȥPathManagement��java�޸�
	// ʶ�������ȥParameterManagement��java�޸�
	public static int algoIndex;

	public static void main(String[] args) throws Exception {
		// ����ϵͳ�ܹ�����dll
		Properties props = System.getProperties();
		String bits = String.valueOf(props.get("sun.arch.data.model"));
		System.out.println("System Architecture: " + bits + " bits System");
		if (Integer.parseInt(bits) == 64) {
			System.loadLibrary("opencv_java330_64");
		} else {
			System.loadLibrary("opencv_java330_86");
		}

		// Scanner inputKey = new Scanner(System.in);

		// ��ӭ
		System.out.println("Welcome running Classified Project Argus");

		// ����������ͳ�ʼ�����ش���
		Mat cover = Imgcodecs.imread(System.getProperty("user.dir") + "/cover.jpg");

		CentralControl.imshow(cover, "SpotSpotter");

		List<String> oldlist = new ArrayList<String>();
		
		while (true) {
			Time.waitFor(100);
			if (CentralControl.hasWorkDir) {
				if (CentralControl.ok2Proceed) {
					oldlist = FIndexReader.getFIndex(CentralControl.monitorPath, false);
					List<String> newlist = new ArrayList<String>();
					newlist = FIndexReader.getFIndex(CentralControl.monitorPath, true);
					newlist.removeAll(oldlist);
					oldlist = newlist;
					
					oldlist = FIndexReader.indexProcess(CentralControl.monitorPath, oldlist);
				}
			}
		}
		
//		while (true) {
//			Time.waitFor(100);
//			if (CentralControl.hasWorkDir) {
//				if (CentralControl.ok2Proceed) {
//					FileListener.autoDeepScan(CentralControl.algoIndex);
//				}
//			}
//		}
		// System.exit(0);
	}
}
	
