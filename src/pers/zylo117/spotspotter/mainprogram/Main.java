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

	// 图片IO路径去PathManagement。java修改
	// 识别参数请去ParameterManagement。java修改
	public static int algoIndex;

	public static void main(String[] args) throws Exception {
		// 根据系统架构载入dll
		Properties props = System.getProperties();
		String bits = String.valueOf(props.get("sun.arch.data.model"));
		System.out.println("System Architecture: " + bits + " bits System");
		if (Integer.parseInt(bits) == 64) {
			System.loadLibrary("opencv_java330_64");
		} else {
			System.loadLibrary("opencv_java330_86");
		}

		// Scanner inputKey = new Scanner(System.in);

		// 欢迎
		System.out.println("Welcome running Classified Project Argus");

		// 载入主封面和初始化主控窗口
		Mat cover = Imgcodecs.imread(System.getProperty("user.dir") + "/cover.jpg");

		CentralControl.imshow(cover, "SpotSpotter");

		List<String> oldlist = new ArrayList<String>();
		
		while (true) {
			Time.waitFor(10000);
			if (CentralControl.hasWorkDir) {
				if (CentralControl.ok2Proceed) {
					BreakPoint.continuous(oldlist);
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
	
