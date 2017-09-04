package pers.zylo117.spotspotter.mainprogram;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.dataio.input.project.PythagorasData;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.Binaryzation;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.pictureprocess.drawer.Draw;
import pers.zylo117.spotspotter.pictureprocess.drawer.DrawPoint;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;
import pers.zylo117.spotspotter.toolbox.Time;
import pers.zylo117.spotspotter.toolbox.mathBox.AngleTransform;
import pers.zylo117.spotspotter.toolbox.mathBox.Line;
import pers.zylo117.spotspotter.toolbox.mathBox.Pointset;
import pers.zylo117.spotspotter.toolbox.mathBox.Regression;
import pers.zylo117.spotspotter.viewer.CentralControl;
import pers.zylo117.spotspotter.viewer.MatView;

public class AlgoList {

	public static void godzilla() throws IOException {

		// �ж��ļ��Ƿ��Ƿ�д��InputStream
		if (null == FileListener.fileName || "".equals(FileListener.fileName)) {
			System.out.println("Input is null");
		}

		String inputimage = PathManagement.inputdir + FileListener.fileName;
		String rawoutputimage = PathManagement.rawoutputdir + FileListener.fileName;
		String bipicoutputimage = PathManagement.bipicdir + FileListener.fileName;
		String finaloutputimage = PathManagement.finaloutputdir + FileListener.fileName;

		Parameter.getParameter(inputimage);
		System.out.println(TargetClassifier.getProcessNameFromPath("Process Name" + inputimage));

		// ��һ��
		// ��ȡ���ؾ���ǰ���ļ��Ƿ���ڽ����ж�
		while (!TargetClassifier.getProcessNameFromPath(inputimage).equals("Unknown")) {
			if (FileOperation.isFileExists(inputimage)) {
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
		Time.waitFor(10);

		String input = FileListener.filePath + "\\" + FileListener.fileName;
		System.out.println(input);
		input = URLDecoder.decode(input, "utf-8");
		// String input =
		// "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/1.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output1.jpg";
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		if (FileOperation.isFileExists(input)) {
			if (FileOperation.isFileNameValid(FileListener.fileName)) {
				Mat imgOrigin = Imgcodecs.imread(input);
				Picture pic = new Picture(imgOrigin);
				pic.fileName = FileListener.fileName;
				pic.fileParent = FileListener.filePath;
				pic.filePath = input;

				pic.processName = TargetClassifier.getProcessNameFromPic(pic);
				if (pic.processName.equals("AA")) {
					// ��ֵ����ó���ROI
					ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, 20);
					// �޳���Ե������ȵľ�ȷROI
					Mat roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP,
							pic.lrP, 4, 3, true, 0.4, 0.4);
					// ROI������ּ���ֵ
					// Mat roi_visiable = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin,
					// pic.ulP, pic.urP, pic.llP, pic.lrP, 11, 13, true,
					// 0.15, 0.3);

					// ��ѡ��ʾ����ͼ��
//					MatView.imshow(imgOrigin, "Original Image");
//					MatView.imshow(roi, "ROI");
					// MatView.imshow(roi_visiable, "ROI_HL");

					// ��ǲ�����Spot
					Mat out = imgOrigin.clone();
					pic.failureData = SpotSpotter.spotList(roi, 0.15);
					// System.out.println(Pointset.centerPoint(spotList).x+"
					// "+Pointset.centerPoint(spotList).y);
					// System.out.println(Pointset.sigma(spotList).x+"
					// "+Pointset.sigma(spotList).y);
					Draw.pointMapList(out, pic.failureData, 10, 1);
					// Draw.pointList(out, Pointset.confidenceIntervals(spotList, 1), 1, 1);
					// Draw.pointList(out, Pointset.pointConnectivity(spotList), 2, 1);
//					MatView.imshow(out, "Output");
						
					// ��ǻع�ֱ��
					if (pic.failureData.size() > 3) {
						Line line = Regression.lineFromMapList(pic.failureData);
						Point startP = new Point(line.solveX(0), 0);
						Point endP = new Point(line.solveX(out.height() - 1), out.height() - 1);
						Draw.line_P2P(out, startP, endP);
						MatView.showPicOnJFrame(out);
					}
					else MatView.showPicOnJFrame(out);
						
					GrandCounter.plusOne();

					PythagorasData.writeNextRow(pic, 0, "A3");
				}
			}
		}
	}
}