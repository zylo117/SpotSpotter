package pers.zylo117.toolbox;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageStream2File {

	public static void IS2F(BufferedImage input, String formatname, String output) throws IOException {
		FileOutputStream fos = new FileOutputStream(output);
		ImageIO.write(input, formatname, fos);
	}
}
