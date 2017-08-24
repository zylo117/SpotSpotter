package pers.zylo117.patternrecognition.facialrecognition;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class StaticFacialRecognition {

	public static void staticFace(String input, String output) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("\nRunning FaceDetector");

		CascadeClassifier faceDetector = new CascadeClassifier(StaticFacialRecognition.class
				.getResource("../../../../../opencv/sources/data/haarcascades_cuda/haarcascade_frontalface_alt.xml")
				.getPath().substring(1));
		Mat image = Imgcodecs.imread(input);
		// 留作备用 StaticFacialRecognition.class.getResource(input).getPath().substring(1)

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
					new Scalar(0, 255, 0));
		}
		
		System.out.println(String.format("Writing %s", output));
		Imgcodecs.imwrite(output, image);
		// 留作备用 StaticFacialRecognition.class.getResource("").getPath().substring(1) + output 
	}
}