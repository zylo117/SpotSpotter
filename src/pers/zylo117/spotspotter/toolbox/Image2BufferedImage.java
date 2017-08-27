package pers.zylo117.spotspotter.toolbox;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Image2BufferedImage {

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// �˴���ȷ����ͼ����������ر�����
		image = new ImageIcon(image).getImage();

		// ���ͼ����͸�����������
		// boolean hasAlpha = hasAlpha(image);

		// ����һ����������Ļ�Ϲ���ĸ�ʽ��bufferedimage
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// ȷ���µĻ���ͼ�����͵�͸����
			int transparency = Transparency.OPAQUE;
			// if (hasAlpha) {
			transparency = Transparency.BITMASK;
			// }

			// ����һ��bufferedimage
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// ϵͳ������һ����Ļ
		}

		if (bimage == null) {
			// ����һ��Ĭ��ɫ�ʵ�bufferedimage
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;
			// if (hasAlpha) {
			type = BufferedImage.TYPE_INT_ARGB;
			// }
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// ��ͼ���Ƶ�bufferedimage��
		Graphics g = bimage.createGraphics();

		// ��ͼ�񻭵�bufferedimage��
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
