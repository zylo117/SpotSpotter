package pers.zylo117.spotspotter.viewer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class ImagePanel extends Panel {

	private final Image screenImage = new BufferedImage(800, 600, 2);

	private final Graphics2D screenGraphic = (Graphics2D) screenImage.getGraphics();

	private Image backgroundImage;

	public ImagePanel(String input) {
		loadImage(input);
		// 设定焦点在本窗体
		setFocusable(true);
		// 设定初始构造时面板大小,这里先采用图片的大小
		setPreferredSize(new Dimension(800, 600));
		// 绘制背景
		drawView();
	}

	/**
	 * 载入图像
	 */
	private void loadImage(String input) {
		// 获得当前类对应的相对位置image文件夹下的背景图像
		ImageIcon icon = new ImageIcon(getClass().getResource(input));
		// 将图像实例赋给backgroundImage
		backgroundImage = icon.getImage();
	}

	private void drawView() {
		screenGraphic.drawImage(backgroundImage, 0, 0, null);
	}

	public void paint(Graphics g) {
		g.drawImage(screenImage, 0, 0, null);
	}

}