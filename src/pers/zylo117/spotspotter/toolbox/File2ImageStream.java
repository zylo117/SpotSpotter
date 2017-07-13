package pers.zylo117.spotspotter.toolbox;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class File2ImageStream {
	public static ImageReader F2IS(String input, String formatname) throws IOException {

		File file = new File(input);

		// ∂¡»°Õº∆¨∏Ò Ω
		formatname = GetPostfixReader.getPostfix(file);
		
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
