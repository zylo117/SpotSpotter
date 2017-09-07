package pers.zylo117.spotspotter.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	public static JFrame jFrame;
	public static JTextField processName_manual, machineNO_manual, productName_manual, binarizationThreshold,
			spotSpotterThreshold, buffTime_manual;
	public static String productN = "XX";
	public static int mcNO = 0, binThresh = 20, ssThresh = 15, buffTime = 20;
	public static int algoIndex = 2;
	public static boolean ok2Proceed, ifPause, ifStop = false, openMonitor = true;

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

		jFrame = new JFrame(windowName);
		JLabel imageView = new JLabel();

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		JMenuBar menubar = new JMenuBar();
		jFrame.setJMenuBar(menubar);
		JMenu menu = new JMenu("Main");
		menubar.add(menu);
		JMenuItem openItem = new JMenuItem("Select Monitoring Path");
		menu.add(openItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		menu.add(exitItem);

		ActionListener act = new ActionListener() {

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
		};

		openItem.addActionListener(act);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		final JScrollPane imageScrollPane = new JScrollPane(imageView);
		// if (image.width() <= 800 && image.height() <= 600)
		// imageScrollPane.setPreferredSize(new Dimension(image.width(),
		// image.height())); // set window size
		// else
		// imageScrollPane.setPreferredSize(new Dimension(600, 620));

		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Image loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));

		JPanel jp1 = new JPanel();
		jp1.setOpaque(false);

		JTextField processName = new JTextField("Process Name");
		processName_manual = new JTextField(3);
		JTextField machineNO = new JTextField("Machine NO");
		machineNO_manual = new JTextField(Integer.toString(mcNO), 3);
		JTextField productName = new JTextField("Product Name");
		productName_manual = new JTextField(productN, 3);

		processName.setEnabled(false); // true可以编辑
		machineNO.setEnabled(false); // true可以编辑
		productName.setEnabled(false); // true可以编辑

		// jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
		// // 设置文本的水平对齐方式
		// jtf4.setHorizontalAlignment(JTextField.CENTER);

		jp1.add(processName);
		jp1.add(processName_manual);
		jp1.add(machineNO);
		jp1.add(machineNO_manual);
		jp1.add(productName);
		jp1.add(productName_manual);

		// 开关按钮
		JButton select = new JButton("Select");
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");

		select.addActionListener(act);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ok2Proceed = true;
				System.out.println("Start Monitoring");
			}
		});

		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ifStop = true;
				System.out.println("Stop Monitoring");
			}
		});

		jp1.add(select);
		jp1.add(start);
		jp1.add(stop);

		JPanel jp2 = new JPanel();
		jp2.setOpaque(false);
		JTextField binThresh = new JTextField("Binarization Threshold");
		binarizationThreshold = new JTextField(Integer.toString(CentralControl.binThresh), 3);
		JTextField ssThresh = new JTextField("SpotSpotter Threshold");
		spotSpotterThreshold = new JTextField(Integer.toString(CentralControl.ssThresh), 3);
		JTextField percent = new JTextField("%");
		JTextField bufferTime = new JTextField("Buffer Time");
		buffTime_manual = new JTextField(Integer.toString(buffTime), 3);
		JTextField ms = new JTextField("ms");

		bufferTime.setEnabled(false);
		ms.setEnabled(false);
		binThresh.setEnabled(false); // true可以编辑
		ssThresh.setEnabled(false); // true可以编辑
		percent.setEnabled(false); // true可以编辑

		jp2.add(bufferTime);
		jp2.add(buffTime_manual);
		jp2.add(ms);
		jp2.add(binThresh);
		jp2.add(binarizationThreshold);
		jp2.add(ssThresh);
		jp2.add(spotSpotterThreshold);
		jp2.add(percent);

		JButton monitor = new JButton("Monitor Switch");

		monitor.addActionListener(new ActionListener() {
			boolean ifMonitorON = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (!ifMonitorON) {
					openMonitor = false;
					ifMonitorON = true;
					jFrame.repaint();
					jFrame.add(imageViewM, BorderLayout.EAST);
					jFrame.pack();
					System.out.println("Turn On Monitor");
				} else {
					openMonitor = true;
					ifMonitorON = false;
					jFrame.repaint();
					jFrame.remove(imageViewM);
					jFrame.pack();
					System.out.println("Turn Off Monitor");
				}

			}
		});

		jp2.add(monitor);

		jFrame.add(jp2, BorderLayout.SOUTH);
		jFrame.add(jp1, BorderLayout.NORTH);
		jFrame.add(imageScrollPane, BorderLayout.CENTER);

		// 并排显示监视窗口
		imageViewM = new JLabel();
		final JScrollPane imageScrollPaneM = new JScrollPane(imageViewM);
		jFrame.add(imageViewM, BorderLayout.EAST);

		jFrame.pack();
		// jFrame.setLocationRelativeTo(null);
		jFrame.setLocation(50, 120);
		jFrame.setVisible(true);
	}

	public static boolean hasWorkDir = false;

	public static JLabel imageViewM;
	public static Image loadedImage;

	public static void showPicOnJFrame(Mat image) {
		if (openMonitor) {
			jFrame.repaint();
			loadedImage = Mat2BufferedImage.mat2BI(image);
			imageViewM.setIcon(new ImageIcon(loadedImage));
			jFrame.pack();
			// jF_overall.setLocationRelativeTo(null);
			// jF_overall.setLocation(650, 120);
			jFrame.setVisible(true);
		}
	}
}
