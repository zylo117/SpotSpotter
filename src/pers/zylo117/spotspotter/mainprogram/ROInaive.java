package pers.zylo117.spotspotter.mainprogram;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ROInaive {

	public static void spitImage(String pic) throws IOException {
		// ����·��
		final String originalImg = pic;

		final File file = new File(originalImg);
		final FileInputStream fis = new FileInputStream(file);
		final BufferedImage image = ImageIO.read(fis);

		// �ָ��2*2(180��*288��)
		final int rows = 4;
		final int cols = 4;
		final int chunks = rows * cols;

		// ����ÿ��Сͼ�Ŀ�Ⱥ͸߶�
		final int chunkWidth = image.getWidth() / cols;
		final int chunkHeight = image.getHeight() / rows;

		int count = 0;
		final BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				// ����Сͼ�Ĵ�С������
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// д��ͼ������
				final Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}

		// ���Сͼ,���Ƿ�����+SSD Raid��Ҫ����,�ؿ���
		for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg", new File("D:/test/piece/" + i + ".jpg"));
		}
		System.out.println("Spitting Finished.");
	}
}
