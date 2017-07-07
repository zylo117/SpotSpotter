package pers.zylo117.spotspotter.patternrecognition;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import pers.zylo117.spotspotter.fileprocessor.PostfixReader;
import pers.zylo117.spotspotter.patternrecognition.GetPixelArray;
import pers.zylo117.spotspotter.pictureprocess.File2ImageStream;

public class Binaryzation {

	public static int[][] bipiccolor;
	
	// Level必须取2、4、8、16、32、64、128，越小精度越高，但是越难计算
	public static void bipic(String input,int level) throws IOException {
		long beginTime = new Date().getTime();

		// 读取图片格式
		File file = new File(input);
		String formatname = PostfixReader.getPostfix(file);
		
		// 图片读入成流
		ImageReader reader = File2ImageStream.F2IS(input, formatname);
		
		int theshold = 256 / level;
		int scale = (int) Math.floor(theshold);
		int[] colorscale = new int[scale];

		// 给颜色区分阀值量级
		// 如level 32，则0、32、64、96、128、160、192、224、256
		colorscale[0] = 0;
		for (int l = 1; l < scale; l++) {
			colorscale[l] = colorscale[l - 1] + level;
		}
		
		bipiccolor = new int[reader.getWidth(0)][reader.getHeight(0)];

		for (int i = 0; i < reader.getWidth(0); i++) {
			for (int j = 0; i < reader.getHeight(0); j++) {
				int temprgb = GetPixelArray.rawoutputRGB[i][j];
				String tempst = Integer.toHexString(temprgb);
				String colorstr = tempst.substring(2, 4);
				int color = Integer.valueOf(colorstr, 16);

				for (int k = 1; k < scale; k++) {
					if (color < colorscale[k] && color > colorscale[k - 1]) {
						bipiccolor[i][j] = colorscale[k - 1];

					if(i==0) {
							System.out.printf("%x\t", bipiccolor[i][j]);
//							System.out.println(color);
					}

					}
				}
			}
		}
		long endTime = new Date().getTime();
		System.out.println("Binaryzation  Tact Time：[" + (endTime - beginTime) + "]ms");
		System.out.println("");
	}
}
