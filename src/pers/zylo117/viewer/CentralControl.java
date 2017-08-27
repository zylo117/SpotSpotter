package pers.zylo117.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

import pers.zylo117.fileprocessor.FileListener;
import pers.zylo117.mainprogram.Main;
import pers.zylo117.mainprogram.PathManagement;
import pers.zylo117.toolbox.GetPostfix;
import pers.zylo117.toolbox.Mat2BufferedImage;

public class CentralControl extends JFrame {
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
				// Ð´ÏÂÄãµÄAction
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String name = chooser.getSelectedFile().getPath();
					String path = chooser.getSelectedFile().getParent();

					String postFix = GetPostfix.fromFilename(name);
					if (postFix.equals("jpg") || postFix.equals("gif") || postFix.equals("bmp")
							|| postFix.equals("tiff") || postFix.equals("JPG")) {
						System.out.println("Temporarily Run Test On A Picture");
						imageView.setIcon(new ImageIcon(name));
					} else {
						PathManagement.monitorPath = path + "\\";
						hasWorkDir = true;
						System.out.println("Monitoring Path Has Been Changed to : " + PathManagement.monitorPath);
//						System.out.println(hasWorkDir);
					}
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
	
	public static boolean hasWorkDir = false;
}
