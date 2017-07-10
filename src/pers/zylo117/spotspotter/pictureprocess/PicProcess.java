package pers.zylo117.spotspotter.pictureprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import pers.zylo117.spotspotter.fileprocessor.FileListener;

public class PicProcess {

	// 设置图片路径
	public static String inputdir = "D:/SpotSpotterWorkLog/input/";
	public static String rawoutputdir = "D:/SpotSpotterWorkLog/rawoutput/";
	public static String bipicdir = "D:/SpotSpotterWorkLog/bipic/";
	public static String finaloutputdir = "D:/SpotSpotterWorkLog/finaloutput/";
	public static String perfect = "D:/SpotSpotterWorkLog/perfect/perfect.jpg";
	public static String IRCF = "D:/SpotSpotterWorkLog/IRCF/";
	public static String ROI = "D:/SpotSpotterWorkLog/ROI/";
	static File fromPic = new File(inputdir); // 原图
	static File toIRCF = new File(IRCF); // 处理后IRCF图片
	static File toROI = new File(ROI); // 处理后ROI图片
	
	// 缩放/旋转 zoom/rotate
	int x = 0;
	int y = 0;
	static double scale = 0;
	static double angle = 0;

	public static void zoom(int x, int y, double scale, double angle) throws IOException {
		// size(x,y)为横纵像素数
		// 注意，thumbnail默认保持横纵比例，以x目标值为参考

		while (true) {
			if (scale == 0) {
				// Thumbnails.of(fromPic).size(100, 100).keepAspectRatio(false);
				// 或者Thumbnails.of(fromPic).forceSize(100,100);
				Thumbnails.of(fromPic.listFiles()).forceSize(x, y).rotate(angle).toFiles(toIRCF,
						Rename.PREFIX_DOT_THUMBNAIL);
				break;
			}
			if (scale == 1) {
				Thumbnails.of(fromPic.listFiles()).size(x, y).rotate(angle).toFiles(toIRCF,
						Rename.PREFIX_DOT_THUMBNAIL);
				break;
			}
			if (scale != 0 && scale != 1 && scale > 0) {
				// 按照比例进行缩小和放大
				// Thumbnails.of(fromPic).scale(0.2f);//按比例缩小到0.2倍
				// Thumbnails.of(fromPic).scale(2f);//按比例放大到2倍
				Thumbnails.of(fromPic.listFiles()).scale(scale).rotate(angle).toFiles(toIRCF,
						Rename.PREFIX_DOT_THUMBNAIL);
				break;
			} else {
				System.out.println("Input Error");
			}
		}
	}

	// 裁剪Crop,不想要这个垃圾
	public static void crop(String fromfile , String IRCFfile, String ROIfile) throws IOException {
		Thumbnails.of(fromfile).sourceRegion(302, 252, 734, 502).size(734, 502).toFile(IRCFfile);
		// 输入ROI的原点,尺寸
		Thumbnails.of(IRCFfile).sourceRegion(82, 86, 570, 340).size(570, 340).toFile(ROIfile);
	}
	
	// Old School Style
	public static void crop2(String infile, String outfile, String format, int x, int y, int w, int h) throws IOException  {
		System.out.println(infile);	
		FileInputStream fis = new FileInputStream(infile);
		FileOutputStream fos = new FileOutputStream(outfile);
		BufferedImage originpic = ImageIO.read(fis);
		BufferedImage ROIpic = originpic.getSubimage(x, y, w, h);
		ImageIO.write(ROIpic, format, fos); 
		originpic = null;
		ROIpic = null;
		fos.close();
		fis.close();
	}
	
}
