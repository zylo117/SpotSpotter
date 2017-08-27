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
		String originalImg = pic;

		File file = new File(originalImg);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(fis);

		// �ָ��2*2(180��*288��)
		int rows = 4;
		int cols = 4;
		int chunks = rows * cols;

		// ����ÿ��Сͼ�Ŀ�Ⱥ͸߶�
		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				// ����Сͼ�Ĵ�С������
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// д��ͼ������
				Graphics2D gr = imgs[count++].createGraphics();
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
