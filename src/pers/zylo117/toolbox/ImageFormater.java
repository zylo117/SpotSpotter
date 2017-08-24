package pers.zylo117.toolbox;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageFormater {

	// 这个不支持bmp图片读取
	public static void convert2jpg(String input, String targetformat, String output) {
		try {
			File file = new File(input);
			file.canRead();
			BufferedImage bimg = ImageIO.read(file);
			ImageIO.write(bimg, targetformat, new File(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
