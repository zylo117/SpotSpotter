package pers.zylo117.spotspotter.mainprogram;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.dataoutput.actualprocessdata.PythagorasData;
import pers.zylo117.spotspotter.fileprocessor.FileDetecter;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Binaryzation;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.pictureprocess.drawer.Draw;
import pers.zylo117.spotspotter.pictureprocess.drawer.DrawPoint;
import pers.zylo117.spotspotter.toolbox.Timer;
import pers.zylo117.spotspotter.toolbox.mathBox.AngleTransform;
import pers.zylo117.spotspotter.toolbox.mathBox.Line;
import pers.zylo117.spotspotter.toolbox.mathBox.Pointset;
import pers.zylo117.spotspotter.toolbox.mathBox.Regression;
import pers.zylo117.spotspotter.viewer.CentralControl;
import pers.zylo117.spotspotter.viewer.MatView;

public class AlgoList {

	public static void godzilla() throws IOException {

		// �ж��ļ��Ƿ��Ƿ�д��InputStream
		if (null == FileListener.filename || "".equals(FileListener.filename)) {
			System.out.println("Input is null");
		}

		String inputimage = PathManagement.inputdir + FileListener.filename;
		String rawoutputimage = PathManagement.rawoutputdir + FileListener.filename;
		String bipicoutputimage = PathManagement.bipicdir + FileListener.filename;
		String finaloutputimage = PathManagement.finaloutputdir + FileListener.filename;

		Parameter.getParameter(inputimage);
		System.out.println(TargetClassifier.getProcessName("Process Name" + inputimage));

		// ��һ��
		// ��ȡ���ؾ���ǰ���ļ��Ƿ���ڽ����ж�
		while (!TargetClassifier.getProcessName(inputimage).equals("Unknown")) {
			if (FileDetecter.isFileExists(inputimage)) {
				// ��ȡ���ؾ���,getData(�ļ�,�����ӳ�ʱ�䣨����Խǿ���ļ�ԽС���ӳ�ԽС��,ʶ����ʼ��x,ʶ����ʼ��y,ʶ�𳤶�,ʶ����)
				GetPixelArray.getData(inputimage, 10, Parameter.ROIstart_x, Parameter.ROIstart_y, Parameter.ROIWidth,
						Parameter.ROIHeight);
				inputimage = null;

				if (GetPixelArray.ready2spot == 1) {
					SpotSpotter.marking(finaloutputimage, Parameter.ROIWidth, Parameter.ROIHeight, 1,
							Parameter.threshold);
				}
				break;
			} else {
				System.out.println("File not exists, skipping");
				break;
			}
		}
	}

	public static void pythagoras_G() throws IOException {
		Timer.waitFor(10);

		String input = PathManagement.monitorPath + FileListener.filename;
		input = URLDecoder.decode(input, "utf-8");
		// String input =
		// "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/1.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output1.jpg";
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		if (FileDetecter.isFileExists(input)) {
			if (FileDetecter.isFileNameValid(FileListener.filename)) {
				Mat imgOrigin = Imgcodecs.imread(input);
				Picture pic = new Picture(imgOrigin);
				pic.fileName = FileListener.filename;

				if (PythagorasData.getType(pic).equals("glue")) {
					// ��ֵ����ó���ROI
					ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, 20);
					// �޳���Ե������ȵľ�ȷROI
					Mat roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP,
							pic.lrP, 2, 2, true, 0.4, 0.4);
					// ROI������ּ���ֵ
					// Mat roi_visiable = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin,
					// pic.ulP, pic.urP, pic.llP, pic.lrP, 11, 13, true,
					// 0.15, 0.3);

					// ��ѡ��ʾ����ͼ��
					MatView.imshow(imgOrigin, "Original Image");
					MatView.imshow(roi, "ROI");
					// MatView.imshow(roi_visiable, "ROI_HL");

					// ��ǲ�����Spot
					Mat out = imgOrigin.clone();
					List<Point> spotList = SpotSpotter.spotList(roi, 0.15);
					// System.out.println(Pointset.centerPoint(spotList).x+"
					// "+Pointset.centerPoint(spotList).y);
					// System.out.println(Pointset.sigma(spotList).x+"
					// "+Pointset.sigma(spotList).y);
					Draw.pointList(out, spotList, 1, 1);
					// Draw.pointList(out, Pointset.confidenceIntervals(spotList, 1), 1, 1);
					// Draw.pointList(out, Pointset.pointConnectivity(spotList), 2, 1);
					MatView.imshow(out, "Output");

					// ��ǻع�ֱ��
					if (spotList.size() > 3) {
						Line line = Regression.line(spotList);
						Point startP = new Point(line.solveX(0), 0);
						Point endP = new Point(line.solveX(out.height() - 1), out.height() - 1);
						Draw.line_P2P(out, startP, endP);
						MatView.imshow(out, "lineOutput");
					}
				}
			}
		}

	}
}