package pers.zylo117.spotspotter.mainprogram;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ROInaive {

	public static void spitImage(String pic) throws IOException {
		// 定义路径
		final String originalImg = pic;

		final File file = new File(originalImg);
		final FileInputStream fis = new FileInputStream(file);
		final BufferedImage image = ImageIO.read(fis);

		// 分割成2*2(180行*288列)
		final int rows = 4;
		final int cols = 4;
		final int chunks = rows * cols;

		// 计算每个小图的宽度和高度
		final int chunkWidth = image.getWidth() / cols;
		final int chunkHeight = image.getHeight() / rows;

		int count = 0;
		final BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				// 设置小图的大小和类型
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// 写入图像内容
				final Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}

		// 输出小图,不是服务器+SSD Raid不要开启,必卡死
		for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg", new File("D:/test/piece/" + i + ".jpg"));
		}
		System.out.println("Spitting Finished.");
	}
}
