package pictureProcess;

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

	public BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// 此代码确保在图像的所有像素被载入
		image = new ImageIcon(image).getImage();

		// 如果图像有透明用这个方法
		// boolean hasAlpha = hasAlpha(image);

		// 创建一个可以在屏幕上共存的格式的bufferedimage
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// 确定新的缓冲图像类型的透明度
			int transparency = Transparency.OPAQUE;
			// if (hasAlpha) {
			transparency = Transparency.BITMASK;
			// }

			// 创造一个bufferedimage
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// 系统不会有一个屏幕
		}

		if (bimage == null) {
			// 创建一个默认色彩的bufferedimage
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			// if (hasAlpha) {
			type = BufferedImage.TYPE_INT_ARGB;
			// }
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// 把图像复制到bufferedimage上
		Graphics g = bimage.createGraphics();

		// 把图像画到bufferedimage上
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
