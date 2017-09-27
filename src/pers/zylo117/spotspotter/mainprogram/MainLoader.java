package pers.zylo117.spotspotter.mainprogram;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.toolbox.Time;

public class MainLoader {

	// 图片IO路径去PathManagement。java修改
	// 识别参数请去ParameterManagement。java修改
//	public static int algoIndex;

	public static void main(String[] args) throws Exception {
		// 根据系统架构载入dll
		final Properties props = System.getProperties();
		final String bits = String.valueOf(props.get("sun.arch.data.model"));
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
		final Mat cover = Imgcodecs.imread(System.getProperty("user.dir") + "/cover.jpg");

		CentralControl.imshow(cover, "SpotSpotter");

		final List<String> oldlist = new ArrayList<String>();
		
		pilot(oldlist);
		
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
	
	// Read existed FileIndex
	
	public static File oldFileIndex, fIndex;
	
	private static void pilot(List<String> oldlist) {
		while (true) {
			Time.waitFor(100);
			if (CentralControl.hasWorkDir) {
				if (CentralControl.ok2Proceed) {
					Time.getTime();
					final String oldIndex = System.getProperty("user.dir") + "\\tmpIndex.dat";
					final String todayFIndex = CentralControl.monitorPath + "\\" + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\" + "findex.dat";
					oldFileIndex = new File(oldIndex);
					fIndex = new File(todayFIndex);
					BreakPoint.continuous(oldlist);
				}
			}
		}
	}
}
	
