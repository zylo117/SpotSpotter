package pers.zylo117.spotspotter.toolbox;

import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageStream2ImageReader {
	public static ImageReader IS2IR(ImageInputStream input, String formatname) throws IOException {

		Iterator<ImageReader> readers;
		ImageReader reader;
		readers = ImageIO.getImageReadersByFormatName(formatname);
		reader = readers.next();
		reader.setInput(input, false);

		return reader;
	}
}
