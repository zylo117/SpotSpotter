package pers.zylo117.spotspotter.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class MatView extends JFrame {
	public static JFrame jF_overall;
	public static JLabel imageView;
	public static Image loadedImage;

	/**
	 * Display Mat image
	 *
	 * @param image
	 * @param windowName
	 */
	public static void imshow(Mat image, String windowName) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		JFrame jFrame = new JFrame(windowName);
		JLabel imageView = new JLabel();
		final JScrollPane imageScrollPane = new JScrollPane(imageView);
		if (image.width() < 800 && image.height() < 600)
			imageScrollPane.setPreferredSize(new Dimension(image.width(), image.height())); // set window size
		else
			imageScrollPane.setPreferredSize(new Dimension(600, 600));
		jFrame.add(imageScrollPane, BorderLayout.CENTER);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		Image loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		jFrame.pack();
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}

	public static void imshow_reDraw(Mat image, String windowName) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		jF_overall = new JFrame(windowName);
		imageView = new JLabel();
		final JScrollPane imageScrollPane = new JScrollPane(imageView);
		imageScrollPane.setPreferredSize(new Dimension(600, 600));
		jF_overall.add(imageScrollPane, BorderLayout.CENTER);
		jF_overall.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		jF_overall.pack();
		jF_overall.setLocationRelativeTo(null);
		jF_overall.setVisible(true);

	}

	public static void showPicOnJFrame(Mat image) {
		jF_overall.repaint();
		loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		jF_overall.pack();
		jF_overall.setLocationRelativeTo(null);
		jF_overall.setVisible(true);
	}
}
