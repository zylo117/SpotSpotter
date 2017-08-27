package pers.zylo117.spotspotter.toolbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class BufferedImage2HQ_ImageFile {
	public static void writeHighQuality(BufferedImage bi, String output, String outputformat, float CompressionQuality)
			throws FileNotFoundException, IOException {
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(outputformat);
		if (iter.hasNext()) {
			ImageWriter writer = iter.next();
			ImageWriteParam param = writer.getDefaultWriteParam();

			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			// ѹ������Ϊ0~1
			param.setCompressionQuality(CompressionQuality);
			FileImageOutputStream out = new FileImageOutputStream(new File(output));
			writer.setOutput(out);
			// writer.write(bi);
			writer.write(null, new IIOImage(bi, null, null), param);
			out.close();
			writer.dispose();
		}
	}
}
