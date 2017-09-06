package pers.zylo117.spotspotter.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;

import pers.zylo117.spotspotter.mainprogram.Main;
import pers.zylo117.spotspotter.mainprogram.PathManagement;
import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class CentralControl extends JFrame {
	
	public static JTextField processName_manual, machineNO_manual, productName_manual, binarizationThreshold, spotSpotterThreshold;
	public static String productN = "XX";
	public static int mcNO = 0, binThresh = 20, ssThresh = 15;
	public static int algoIndex = 2;
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
				// 写下你的Action
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					String name = chooser.getSelectedFile().getPath();
					String path = chooser.getSelectedFile().getParent();

					String postFix = GetPostfix.fromFilename(name);
					if (postFix.equals("jpg") || postFix.equals("gif") || postFix.equals("bmp") || postFix.equals("png")
							|| postFix.equals("tiff") || postFix.equals("JPG")) {
						System.out.println("Temporarily Run Test On A Picture");
						imageView.setIcon(new ImageIcon(name));
					} else {
						PathManagement.monitorPath = path + "\\";
						hasWorkDir = true;
						System.out.println("Monitoring Path Has Been Changed to : " + PathManagement.monitorPath);
						// System.out.println(hasWorkDir);
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
//		if (image.width() <= 800 && image.height() <= 600)
//			imageScrollPane.setPreferredSize(new Dimension(image.width(), image.height())); // set window size
//		else
//			imageScrollPane.setPreferredSize(new Dimension(600, 620));
		
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Image loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		
		JPanel jp1 = new JPanel();
		jp1.setOpaque(false);
		
		JTextField processName = new JTextField("Process Name");
		processName_manual = new JTextField(3);
		JTextField machineNO = new JTextField("Machine NO");
		machineNO_manual = new JTextField(3);
		JTextField productName = new JTextField("Product Name");
		productName_manual = new JTextField(3);


		processName.setEnabled(false); // true可以编辑
		machineNO.setEnabled(false); // true可以编辑
		productName.setEnabled(false); // true可以编辑

//		jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
//		// 设置文本的水平对齐方式
//		jtf4.setHorizontalAlignment(JTextField.CENTER);

		jp1.add(processName);
		jp1.add(processName_manual);
		jp1.add(machineNO);
		jp1.add(machineNO_manual);
		jp1.add(productName);
		jp1.add(productName_manual);

		
		JPanel jp2 = new JPanel();
		jp2.setOpaque(false);
		JTextField binThresh = new JTextField("Binarization Threshold");
		binarizationThreshold = new JTextField(3);
		JTextField ssThresh = new JTextField("SpotSpotter Threshold");
		spotSpotterThreshold = new JTextField(3);
		JTextField percent = new JTextField("%");
		
		binThresh.setEnabled(false); // true可以编辑
		ssThresh.setEnabled(false); // true可以编辑
		percent.setEnabled(false); // true可以编辑
		
		jp2.add(binThresh);
		jp2.add(binarizationThreshold);
		jp2.add(ssThresh);
		jp2.add(spotSpotterThreshold);
		jp2.add(percent);

		jFrame.add(jp2, BorderLayout.SOUTH,0);
		jFrame.add(jp1, BorderLayout.NORTH,0);
		jFrame.add(imageScrollPane, BorderLayout.CENTER,-1);
		jFrame.pack();
//		jFrame.setLocationRelativeTo(null);
		jFrame.setLocation(50, 120);
		jFrame.setVisible(true);
	}

	public static boolean hasWorkDir = false;
}
