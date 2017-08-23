package pers.zylo117.spotspotter.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class MatView extends JFrame {
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

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		JMenuBar menubar = new JMenuBar();
		jFrame.setJMenuBar(menubar);
		JMenu menu = new JMenu("File");
		menubar.add(menu);
		JMenuItem openItem = new JMenuItem("Open");
		menu.add(openItem);
		JMenuItem exitItem = new JMenuItem("Close");
		menu.add(exitItem);
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
//					String name = chooser.getSelectedFile().getPath();
//					imageView.setIcon(new ImageIcon(name));
					// Ð´ÏÂÄãµÄAction
					
				}
			}
		});
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

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
}
