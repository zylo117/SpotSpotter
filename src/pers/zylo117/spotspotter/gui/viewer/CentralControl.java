package pers.zylo117.spotspotter.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import org.opencv.core.Mat;

import pers.zylo117.spotspotter.gui.textbox.ConsoleTextArea;
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
	public static boolean ok2Proceed, ifPause, ifStop = false, openPicMonitor = true, openLogMonitor = true;

	public static boolean hasWorkDir = false;

	public static JLabel imageViewM;
	public static JPanel logview;
	public static Image loadedImage;
	public static String monitorPath;
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
						monitorPath = path + "\\";
						hasWorkDir = true;
						System.out.println("Monitoring Path Has Been Changed to : " + monitorPath);
						// System.out.println(hasWorkDir);
						jFrame.repaint();
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

		// Panel1基础信息文本框***********************************
		JPanel baseInfo = new JPanel();
		baseInfo.setOpaque(false);

		JTextField processName = new JTextField("Process");
		processName_manual = new JTextField(3);
		JTextField machineNO = new JTextField("MachineNO");
		machineNO_manual = new JTextField(Integer.toString(mcNO), 3);
		JTextField productName = new JTextField("Product");
		productName_manual = new JTextField(productN, 3);

		processName.setEnabled(false); // true可以编辑
		machineNO.setEnabled(false); // true可以编辑
		productName.setEnabled(false); // true可以编辑

		// jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
		// // 设置文本的水平对齐方式
		// jtf4.setHorizontalAlignment(JTextField.CENTER);

		baseInfo.add(processName);
		baseInfo.add(processName_manual);
		baseInfo.add(machineNO);
		baseInfo.add(machineNO_manual);
		baseInfo.add(productName);
		baseInfo.add(productName_manual);

		
		// Panel2参数文本框***********************************
		JPanel paraMeter = new JPanel();
		paraMeter.setOpaque(false);
		JTextField binThresh = new JTextField("BinarizationThreshold");
		binarizationThreshold = new JTextField(Integer.toString(CentralControl.binThresh), 3);
		JTextField ssThresh = new JTextField("SpotSpotterThreshold");
		spotSpotterThreshold = new JTextField(Integer.toString(CentralControl.ssThresh), 3);
		JTextField percent = new JTextField("%");
		JTextField bufferTime = new JTextField("BufferTime");
		buffTime_manual = new JTextField(Integer.toString(buffTime), 3);
		JTextField ms = new JTextField("ms");

		bufferTime.setEnabled(false);
		ms.setEnabled(false);
		binThresh.setEnabled(false); // true可以编辑
		ssThresh.setEnabled(false); // true可以编辑
		percent.setEnabled(false); // true可以编辑

		paraMeter.add(bufferTime);
		paraMeter.add(buffTime_manual);
		paraMeter.add(ms);
		paraMeter.add(binThresh);
		paraMeter.add(binarizationThreshold);
		paraMeter.add(ssThresh);
		paraMeter.add(spotSpotterThreshold);
		paraMeter.add(percent);

		// Panel3开关按钮*******************************************
		JPanel switchPanel = new JPanel();
		switchPanel.setOpaque(true);
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

		JButton picMonitor = new JButton("Monitor");

		picMonitor.addActionListener(new ActionListener() {
			boolean ifPicMonitorON = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (!ifPicMonitorON) {
					openPicMonitor = false;
					ifPicMonitorON = true;
					jFrame.repaint();
					jFrame.add(imageViewM, BorderLayout.CENTER);
					jFrame.pack();
					System.out.println("Turn On Picture Monitor");
				} else {
					openPicMonitor = true;
					ifPicMonitorON = false;
					jFrame.repaint();
					jFrame.remove(imageViewM);
					jFrame.pack();
					System.out.println("Turn Off Picture Monitor");
				}

			}
		});
		
		JButton logMonitor = new JButton("Log");

		logMonitor.addActionListener(new ActionListener() {
			boolean ifLogMonitorON = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (!ifLogMonitorON) {
					openLogMonitor = false;
					ifLogMonitorON = true;
					jFrame.repaint();
					jFrame.add(logview, BorderLayout.EAST);
					jFrame.pack();
					System.out.println("Turn On Log Monitor");
				} else {
					openLogMonitor = true;
					ifLogMonitorON = false;
					jFrame.repaint();
					jFrame.remove(logview);
					jFrame.pack();
					System.out.println("Turn Off Log Monitor");
				}

			}
		});
		
		switchPanel.add(select);
		switchPanel.add(start);
		switchPanel.add(stop);
		switchPanel.add(picMonitor);
		switchPanel.add(logMonitor);
		
//		// Panel4当前监视路径*******************************************
//		JPanel currentPath = new JPanel();
//		currentPath.setOpaque(false);
//		JTextField path = new JTextField(monitorPath, 80);
//		currentPath.add(path);
		
		JPanel overallCtrl = new JPanel();
		overallCtrl.setLayout(new GridLayout(3, 1));
		overallCtrl.add(switchPanel);
		overallCtrl.add(baseInfo);
		overallCtrl.add(paraMeter);
//		overallCtrl.add(currentPath);

		jFrame.add(overallCtrl, BorderLayout.SOUTH);
		jFrame.add(imageScrollPane, BorderLayout.WEST);

		// 并排显示监视窗口
		imageViewM = new JLabel();
		final JScrollPane imageScrollPaneM = new JScrollPane(imageViewM);
		jFrame.add(imageViewM, BorderLayout.CENTER);

		// 监视窗口右边显示Log
		logview = new JPanel();
		logview.add(printOnLogMonitor());
		logview.setMaximumSize(new Dimension(100, 600));
		jFrame.add(logview, BorderLayout.EAST);
		
		jFrame.pack();
		// jFrame.setLocationRelativeTo(null);
		jFrame.setLocation(50, 50);
		jFrame.setVisible(true);
	}

	public static JScrollPane printOnLogMonitor() {
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
		} catch (IOException e) {
			System.err.println("不能创建LoopedStreams：" + e);
			System.exit(1);
		}
		return new JScrollPane(consoleTextArea);
	}
	
	public static void showPicOnJFrame(Mat image) {
		if (openPicMonitor) {
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
