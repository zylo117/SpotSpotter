package pers.zylo117.spotspotter.patternrecognition;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import javax.imageio.ImageReader;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.toolbox.File2ImageReader;
import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.ImageStream2File;
import pers.zylo117.spotspotter.viewer.MatView;

public class Binaryzation {

	public static int[][] bipiccolor;

	// Level必须取2、4、8、16、32、64、128，越小精度越高，但是越难计算
	public static void bipic(String input, String output, int level) throws IOException {
		long beginTime = new Date().getTime();

		// 读取图片格式
		String formatname = GetPostfix.fromFilepath(input);

		// 图片读入成流
		ImageReader reader = File2ImageReader.F2IR(input);

		int theshold = 256 / level;
		int scale = (int) Math.floor(theshold);
		int[] colorscale = new int[scale + 1];

		// 给颜色区分阀值量级
		// 如level 32，则0、32、64、96、128、160、192、224、256
		colorscale[0] = 0;
		for (int l = 1; l <= scale; l++) {
			colorscale[l] = colorscale[l - 1] + level;
		}

		int width = (int) Math.floor(reader.getWidth(0));
		int height = (int) Math.floor(reader.getHeight(0));

		bipiccolor = new int[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				String tempst = Integer.toHexString(GetPixelArray.rawoutputRGB[i][j]);
				String colorstr;
				
				if (tempst.length() >= 6) {
					colorstr = tempst.substring(2, 4);
					
				} else if (tempst.length() == 5) {
					colorstr = tempst.substring(1, 3);
					
				} else if (tempst.length() == 1) {
					String colorstr1 = tempst.substring(0, 1);
					colorstr = "0" + colorstr1;
				} else
					colorstr = "00";
				
				int color = Integer.valueOf(colorstr, 16);

//				System.out.println("rgb"+temprgb[i][j]);
//				System.out.println("tempst" + tempst);
//				System.out.println("cst" + colorstr);
//				System.out.println("col"+color);

				for (int k = 1; k <= scale; k++) {
					if (color < colorscale[k] && color > colorscale[k - 1]) {
						String bicolorstr = Integer.toHexString(colorscale[k - 1]);
						String bicolorstr2 = bicolorstr + bicolorstr + bicolorstr;
						bipiccolor[i][j] = Integer.valueOf(bicolorstr2, 16);
					}
				}

//				if (i == 0) {
//					System.out.println(bipiccolor[i][j]);
//				}
			}
		}
		
		// 写入上述ARGB信息到二值化缓存图像，并写入到原始图像路径
		BufferedImage bibimg;
		if (formatname.equals("png")) {
			bibimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			bibimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				bibimg.setRGB(i, j, bipiccolor[i][j]);
			}
		}
		ImageStream2File.IS2F(bibimg, formatname, output);
		
		long endTime = new Date().getTime();
		System.out.println("Binaryzation Tact Time:[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}
	
	public static Mat binaryzation_OpenCV(Mat input, double thresh) {
		Mat gray = new Mat();
		Imgproc.cvtColor(input, gray, Imgproc.COLOR_RGB2GRAY);
		Mat binImg = new Mat();
		Imgproc.threshold(gray, binImg, thresh, 255, Imgproc.THRESH_BINARY);
		return binImg;
	}
	
	public static void main(String[] args) throws Exception {
		String input = "D:\\11#SUT 4\\4.jpg";
		String output = "D:/workspace/SpotSpotter/src/pers/zylo117/spotspotter/image/output2.jpg";
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat in = Imgcodecs.imread(input);
		if (in.empty()) {
			throw new Exception("no file");
		}
		Mat mask = binaryzation_OpenCV(in, 15);
		MatView.imshow(mask, "mask");
//		Imgcodecs.imwrite(output, mask);
	}
}
