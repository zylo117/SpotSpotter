package patternRecognition;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class test {
	
	public static void testcolor(String src){
        long beginTime = new Date().getTime();
        File file = new File(src);
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
			System.out.println(src);
            System.out.println("width:" + reader.getWidth(0));
            System.out.println("height:" + reader.getHeight(0));
            reader.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = new Date().getTime();
        System.out.println("使用[ImageReader]获取图片尺寸耗时：[" + (endTime - beginTime)+"]ms");
    }
	
}
