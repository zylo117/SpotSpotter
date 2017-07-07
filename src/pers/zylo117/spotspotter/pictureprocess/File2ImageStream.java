package pers.zylo117.spotspotter.pictureprocess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import pers.zylo117.spotspotter.fileprocessor.PostfixReader;

public class File2ImageStream {
	public static ImageReader F2IS(String input, String formatname) throws IOException {

		File file = new File(input);

		// ∂¡»°Õº∆¨∏Ò Ω
		formatname = PostfixReader.getPostfix(file);
		
		Iterator<ImageReader> readers;
		ImageReader reader;
		ImageInputStream iis;
		readers = ImageIO.getImageReadersByFormatName(formatname);
		reader = (ImageReader) readers.next();
		iis = ImageIO.createImageInputStream(file);
		reader.setInput(iis, false);
		
		return reader;
	}
}
