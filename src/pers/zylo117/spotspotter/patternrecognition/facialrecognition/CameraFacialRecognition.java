package pers.zylo117.spotspotter.patternrecognition.facialrecognition;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class CameraFacialRecognition extends JPanel {
	private static final long serialVersionUID = 1L;

	private BufferedImage mImg;

	private static Mat dobj(CascadeClassifier objDetector, Mat src) {
		final Mat dst = src.clone();

		final MatOfRect objDetections = new MatOfRect();

		objDetector.detectMultiScale(dst, objDetections);

		if (objDetections.toArray().length <= 0) {
			return src;
		}
		for (final Rect rect : objDetections.toArray()) {
			Imgproc.rectangle(dst, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 0, 255), 2);
		}
		return dst;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
		}
	}

	public static void CamFaceDetector() {
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

			final CascadeClassifier objDetector = new CascadeClassifier(CameraFacialRecognition.class
					.getResource("../../../../../opencv/sources/data/lbpcascades/lbpcascade_frontalface_improved.xml")
					.getPath().substring(1));

			final Mat capImg = new Mat();
			final VideoCapture capture = new VideoCapture(0);
			final int height = (int) capture.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT);
			final int width = (int) capture.get(Videoio.CV_CAP_PROP_FRAME_WIDTH);
			if (height == 0 || width == 0) {
				throw new Exception("camera not found");
			}

			final JFrame frame = new JFrame("camera");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			final CameraFacialRecognition panel = new CameraFacialRecognition();
			frame.setContentPane(panel);
			frame.setVisible(true);
			frame.setSize(width + frame.getInsets().left + frame.getInsets().right,
					height + frame.getInsets().top + frame.getInsets().bottom);

			Mat dst = new Mat();
			while (frame.isShowing()) {
				capture.read(capImg);
				dst = dobj(objDetector, capImg);
				panel.mImg = Mat2BufferedImage.mat2BI(dst);
				panel.repaint();
			}
			capture.release();
		} catch (final Exception e) {
			System.out.println("Exception" + e);
		} finally {
			System.out.println("--done--");
		}
	}
}