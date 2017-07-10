package pers.zylo117.spotspotter.pictureprocess;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageReader2File {

	public static void IR2F(ImageReader input, String formatname, String output) throws IOException {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(formatname);
		ImageWriter writer = writers.next();
		File f = new File(output);
		ImageOutputStream ios = ImageIO.createImageOutputStream(f);
		writer.setOutput(ios);
	}
}
