package pers.zylo117.spotspotter.mainprogram;

import java.io.IOException;
import java.net.URLDecoder;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.fileprocessor.FileOperation;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.gui.viewer.MatView;
import pers.zylo117.spotspotter.dataio.input.project.PythagorasData;
import pers.zylo117.spotspotter.dataio.output.email.PythagorasEMail;
import pers.zylo117.spotspotter.fileprocessor.FileListener;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.patternrecognition.ROI_Irregular;
import pers.zylo117.spotspotter.patternrecognition.corealgorithm.SpotSpotter;
import pers.zylo117.spotspotter.patternrecognition.regiondetector.ProjectPR.ProjectAlgo_Qiu2017;
import pers.zylo117.spotspotter.pictureprocess.Picture;
import pers.zylo117.spotspotter.pictureprocess.Resize;
import pers.zylo117.spotspotter.pictureprocess.drawer.Draw;
import pers.zylo117.spotspotter.toolbox.BufferedImage2HQ_ImageFile;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;
import pers.zylo117.spotspotter.toolbox.Time;
import pers.zylo117.spotspotter.toolbox.mathBox.Line;
import pers.zylo117.spotspotter.toolbox.mathBox.MathBox;
import pers.zylo117.spotspotter.toolbox.mathBox.Regression;

public class AlgoList {

	public static void godzilla() throws IOException {

		// 判断文件是否是否写入InputStream
		if (null == FileListener.fileName || "".equals(FileListener.fileName)) {
			System.out.println("Input is null");
		}

		String inputimage = PathManagement.inputdir + FileListener.fileName;
		String rawoutputimage = PathManagement.rawoutputdir + FileListener.fileName;
		String bipicoutputimage = PathManagement.bipicdir + FileListener.fileName;
		String finaloutputimage = PathManagement.finaloutputdir + FileListener.fileName;

		Parameter.getParameter(inputimage);
		System.out.println(TargetClassifier.getProcessNameFromPath("Process Name" + inputimage));

		// 第一项
		// 提取像素矩阵前对文件是否存在进行判断
		while (!TargetClassifier.getProcessNameFromPath(inputimage).equals("Unknown")) {
			if (FileOperation.isFileExists(inputimage)) {
				// 提取像素矩阵,getData(文件,缓冲延迟时间（机器越强，文件越小，延迟越小）,识别起始点x,识别起始点y,识别长度,识别宽度)
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
		Time.waitFor(CentralControl.buffTime);

		// 从主控窗口获取数据
		if (!CentralControl.machineNO_manual.getText().isEmpty())
			CentralControl.mcNO = Integer.parseInt(CentralControl.machineNO_manual.getText());
		if (!CentralControl.productName_manual.getText().isEmpty())
			CentralControl.productN = CentralControl.productName_manual.getText();
		
		if (!CentralControl.buffTime_manual.getText().isEmpty())
			CentralControl.buffTime = Integer.parseInt(CentralControl.buffTime_manual.getText());
		if (!CentralControl.binarizationThreshold.getText().isEmpty())
			CentralControl.binThresh = Integer.parseInt(CentralControl.binarizationThreshold.getText());
		if (!CentralControl.spotSpotterThreshold.getText().isEmpty())
			CentralControl.ssThresh = Integer.parseInt(CentralControl.spotSpotterThreshold.getText());
		if (!CentralControl.mosaicLength_manual.getText().isEmpty())
			CentralControl.mosaicLength = Integer.parseInt(CentralControl.mosaicLength_manual.getText());

		String input = FileListener.filePath + "\\" + FileListener.fileName;
		// System.out.println(input);
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

				Mat roi = new Mat();

				pic.processName = TargetClassifier.getProcessNameFromPic(pic);
				if (pic.processName.equals("AA")) {
					// 二值化获得初步ROI
					ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, CentralControl.binThresh);

					// 剔除边缘、角落等的精确ROI
					roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP, pic.lrP, 3,
							3, true, 0.5, 0.45);
				} else if (pic.processName.equals("GA")) {
					// 二值化获得初步ROI
					ProjectAlgo_Qiu2017.colorProject_Qiu2017(imgOrigin, CentralControl.binThresh);
					// ROI
					roi = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin, pic.ulP, pic.urP, pic.llP, pic.lrP,
							5, 5, false, 0, 0);
				}
				// ROI按区域分级阀值
				// Mat roi_visiable = ROI_Irregular.irregularQuadrangle_Simplified(imgOrigin,
				// pic.ulP, pic.urP, pic.llP, pic.lrP, 11, 13, true,
				// 0.15, 0.3);

				// 可选显示步骤图像
				// MatView.imshow(imgOrigin, "Original Image");
				// MatView.imshow(roi, "ROI");
				// MatView.imshow(roi_visiable, "ROI_HL");

				// 标记并计数Spot
				Mat out = imgOrigin.clone();
				pic.failureData = SpotSpotter.spotList(roi, (double) CentralControl.ssThresh / 100);
					// System.out.println(Pointset.centerPoint(spotList).x+"
				// "+Pointset.centerPoint(spotList).y);
				// System.out.println(Pointset.sigma(spotList).x+"
				// "+Pointset.sigma(spotList).y);
				
				// 圈出spot
				if (pic.processName.equals("AA")) {
					Draw.pointMapList(out, pic.failureData, 10, 1, CentralControl.mosaicLength);
				} else if (pic.processName.equals("GA")) {
					Draw.pointMapList(out, pic.failureData, 500, 5, CentralControl.mosaicLength);
				}
				// Draw.pointList(out, Pointset.confidenceIntervals(spotList, 1), 1, 1);
				// Draw.pointList(out, Pointset.pointConnectivity(spotList), 2, 1);
				// MatView.imshow(out, "Output");

				// 压缩并显示
				Mat imgOriginClone = Resize.tillFit(imgOrigin, 512, 512);
				CentralControl.showPicOnPre(imgOriginClone);

				// 画出ROI
				Draw.line_P2P(out, pic.ulP, pic.llP);
				Draw.line_P2P(out, pic.ulP, pic.urP);
				Draw.line_P2P(out, pic.urP, pic.lrP);
				Draw.line_P2P(out, pic.llP, pic.lrP);

				// System.out.print(MathBox.pointDistance(pic.ulP, pic.llP));
				// System.out.print(MathBox.pointDistance(pic.ulP, pic.urP));

				// 标记回归直线
				if (pic.processName.equals("AA") && pic.failureData.size() > 3) {
					Line line = Regression.lineFromMapList(pic.failureData);
					Point startP = new Point(line.solveX(0) * CentralControl.mosaicLength, 0);
					Point endP = new Point(line.solveX(out.height() - 1) * CentralControl.mosaicLength, (out.height() - 1) * CentralControl.mosaicLength);
					Draw.line_P2P(out, startP, endP);
					pic.material = "Glue";
					Mat outClone = Resize.tillFit(out, 512, 512);
					CentralControl.showPicOnPost(outClone);
				} else {
					pic.material = "Dust";
					Mat outClone = Resize.tillFit(out, 512, 512);
					CentralControl.showPicOnPost(outClone);
				}

				GrandCounter.plusOne();

				PythagorasData.writeNextRow(pic, 0, "A3");
				if (pic.result().equals("NG")) {
					System.out.println("Test Result: " + pic.material);
					System.out.println("Outputing NG Pics");
					Time.getTime();
					String path = System.getProperty("user.dir") + "\\" + pic.processName + "\\" + Time.year + "\\"
							+ Time.month + "\\NGPics\\";
					FileOperation.createDir(path);
//					Imgcodecs.imwrite(path + pic.fileNameWOPostfix() + pic.postFixWithDot(), imgOrigin);
					BufferedImage2HQ_ImageFile.writeHighQuality(Mat2BufferedImage.mat2BI(imgOrigin),
							path + pic.fileNameWOPostfix() + pic.postFixWithDot(), "jpg", 1);
//					Imgcodecs.imwrite(path + pic.fileNameWOPostfix() + "_NG" + pic.postFixWithDot(), out);
					BufferedImage2HQ_ImageFile.writeHighQuality(Mat2BufferedImage.mat2BI(out),
							path + pic.fileNameWOPostfix() + "_NG" + pic.postFixWithDot(), "jpg", 1);
				} else
					System.out.println("Test Result: OK");

				PythagorasEMail.writeEMail();

			}
		}
	}
}