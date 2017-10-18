package pers.zylo117.spotspotter.toolbox;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class File2ImageReader {
	public static ImageReader F2IR(String input) throws IOException {

		final File file = new File(input);

		// ∂¡»°Õº∆¨∏Ò Ω
		final String formatname = GetPostfix.fromFilepath(input);

		Iterator<ImageReader> readers;
		ImageReader reader;
		ImageInputStream iis;
		readers = ImageIO.getImageReadersByFormatName(formatname);
		reader = readers.next();
		iis = ImageIO.createImageInputStream(file);
		reader.setInput(iis, false);

		return reader;
	}
}
