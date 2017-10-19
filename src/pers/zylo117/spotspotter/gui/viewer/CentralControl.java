package pers.zylo117.spotspotter.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import org.opencv.core.Mat;

import pers.zylo117.spotspotter.dataio.input.IPReader;
import pers.zylo117.spotspotter.gui.textbox.ConsoleTextArea;
import pers.zylo117.spotspotter.mainprogram.AlgoList;
import pers.zylo117.spotspotter.toolbox.GetPostfix;
import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

public class CentralControl extends JFrame {

	public static JFrame jFrame;
	public static JTextField shortCut, machineNO_manual, productName_manual, binarizationThreshold,
			spotSpotterThreshold, buffTime_manual, mosaicLength_manual, offsetText, ioPulseText;
	public static String productN = "XX";
	public static int mcNO = 0, binThresh = 300, ssThresh = 3, buffTime = 1, mosaicLength = 1, offset = 10,
			ioPulse = 20;
	public static int algoIndex = 2, counter = 0;
	public static boolean ok2Proceed, ok2Test, ok2Exit = false, ifTemp = false, ifPause, ifStop = false,
			openPicMonitor = true, openLogMonitor = true, ifEngMode = false;

	public static boolean hasWorkDir = false;

	public static JLabel imageView, imageViewM;
	public static JPanel logview;
	public static Image loadedImage;
	public static String monitorPath = null;
	public static Container logContainer;
	public static File tempFile;

	/**
	 * Display Mat image
	 *
	 * @param image
	 * @param windowName
	 */
	public static void imshow(Mat image, String windowName) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		jFrame = new JFrame(windowName);
		// jFrame.setMaximumSize(new Dimension(1280, 768));
		imageView = new JLabel();

		final JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		final JMenuBar menubar = new JMenuBar();
		jFrame.setJMenuBar(menubar);
		final JMenu menu = new JMenu("Menu");
		menubar.add(menu);
		final JMenuItem openItem = new JMenuItem("Select Monitoring Path");
		menu.add(openItem);
		final JMenuItem exitItem = new JMenuItem("Force Close");
		menu.add(exitItem);

		final ActionListener act_start = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// 写下你的Action
				// 只选择目录
				jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				final int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					final File file = chooser.getSelectedFile();
					final String name = chooser.getSelectedFile().getPath();
					final String path = chooser.getSelectedFile().getParent();

					final String postFix = GetPostfix.fromFilename(name);
					if (postFix.equals("jpg") || postFix.equals("gif") || postFix.equals("bmp") || postFix.equals("png")
							|| postFix.equals("tiff") || postFix.equals("JPG")) {
						System.out.println("Temporarily Run Test On A Picture");
						AlgoList.panda(name);
					} else if (postFix.equals("dat")) {
						hasWorkDir = true;
						System.out.println("Temporarily Run Test On A List");
						tempFile = file;
						ifTemp = true;
						ok2Test = true;
					} else if (file.isFile()) {
						monitorPath = path + "\\";
						hasWorkDir = true;
						System.out.println("Monitoring Path Hooked to : " + monitorPath);
						System.out.println("Start Monitoring");
						// System.out.println(hasWorkDir);
						jFrame.repaint();
						ok2Proceed = true;
					} else {
						monitorPath = name + "\\";
						hasWorkDir = true;
						System.out.println("Monitoring Path Has Been Changed to : " + monitorPath);
						System.out.println("Start Monitoring");
						// System.out.println(hasWorkDir);
						jFrame.repaint();
						ok2Proceed = true;
					}
				}
			}
		};

		final ActionListener act_exit = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				ok2Exit = true;
			}
		};

		openItem.addActionListener(act_start);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);
			}
		});

		final JScrollPane main = new JScrollPane(imageView);
		// if (image.width() <= 800 && image.height() <= 600)
		// imageScrollPane.setPreferredSize(new Dimension(image.width(),
		// image.height())); // set window size
		// else
		// imageScrollPane.setPreferredSize(new Dimension(600, 620));

		final Image loadedImage = Mat2BufferedImage.mat2BI(image);
		imageView.setIcon(new ImageIcon(loadedImage));
		final JScrollPane coverCopy = main;

		// 监视窗口右边显示Log*************************************
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
		} catch (final IOException e) {
			System.err.println("Unable to create LoopedStreams：" + e);
			System.exit(1);
		}
		final JScrollPane consolePane = new JScrollPane(consoleTextArea);
		final Rectangle boundsOfCover = imageView.getBounds();
		consolePane.setBounds(boundsOfCover.x + boundsOfCover.width / 4, boundsOfCover.y, boundsOfCover.width,
				boundsOfCover.height);
		consolePane.setPreferredSize(new Dimension(500, 100));

		// Panel1基础信息文本框***********************************
		final JPanel baseInfo = new JPanel();
		baseInfo.setOpaque(false);

		final JTextField machineNO = new JTextField("MachineNO");
		machineNO_manual = new JTextField(Integer.toString(mcNO), 3);
		final JTextField productName = new JTextField("Product");
		productName_manual = new JTextField(productN, 3);
		final JTextField offsetBox = new JTextField("Offset");
		offsetText = new JTextField(Integer.toString(offset), 3);
		final JTextField ioPulseBox = new JTextField("I/O Feq");
		ioPulseText = new JTextField(Integer.toString(ioPulse), 3);
		final JTextField engModeBox = new JTextField("More Data");
		final JComboBox<String> engMode = new JComboBox<>();
		engMode.addItem("Disable");
		engMode.addItem("Enable");
		engMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				if (engMode.getSelectedItem().equals("Enable")) {
					ifEngMode = true;
					System.out.println("Engineer Mode Endable, All Data Will Be Saved");
					System.out.println("Warning! Too Much Data May Cause Memory Leak!");
				} else {
					ifEngMode = false;
				}

			}
		});

		final JTextField shortCutBox = new JTextField("Auto Run");
		final JComboBox<String> shortCut = new JComboBox<>();
		shortCut.addItem("None");
		for (int i = 1; i < macCount + 1; i++) {
			shortCut.addItem("GA" + i);
		}
		shortCut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				final int macNO = shortCut.getSelectedIndex();
				if (macNO > 1) {
					loadIP(macNO - 1);
					monitorPath = findPath();
					hasWorkDir = true;
					System.out.println("Auto Run on " + shortCut.getSelectedItem());
					System.out.println("Start Monitoring");
					System.out.println("On: " + monitorPath);
					jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					// System.out.println(hasWorkDir);

					machineNO_manual.setText(Integer.toString(macNO));
					productName_manual.setText(category);

					machineNO_manual.setEnabled(false);
					productName_manual.setEnabled(false);

					jFrame.repaint();
					ok2Proceed = true;
				}
			}
		});

		shortCutBox.setEnabled(false); // true可以编辑
		machineNO.setEnabled(false); // true可以编辑
		productName.setEnabled(false); // true可以编辑
		offsetBox.setEnabled(false);
		ioPulseBox.setEnabled(false);
		engModeBox.setEnabled(false);

		// jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
		// // 设置文本的水平对齐方式
		// jtf4.setHorizontalAlignment(JTextField.CENTER);

		baseInfo.add(machineNO);
		baseInfo.add(machineNO_manual);
		baseInfo.add(productName);
		baseInfo.add(productName_manual);
		baseInfo.add(offsetBox);
		baseInfo.add(offsetText);
		baseInfo.add(ioPulseBox);
		baseInfo.add(ioPulseText);

		// Panel2参数文本框***********************************
		final JPanel paraMeter = new JPanel();
		paraMeter.setOpaque(false);
		final JTextField binThresh = new JTextField("BinThresh");
		binarizationThreshold = new JTextField(Integer.toString(CentralControl.binThresh), 3);
		final JTextField ssThresh = new JTextField("DustThresh");
		spotSpotterThreshold = new JTextField(Integer.toString(CentralControl.ssThresh), 3);
		final JTextField mosaicL = new JTextField("BlockSize");
		mosaicLength_manual = new JTextField(Integer.toString(CentralControl.mosaicLength), 3);
		final JTextField percent = new JTextField("%");
		final JTextField bufferTime = new JTextField("BufferTime");
		buffTime_manual = new JTextField(Integer.toString(buffTime), 3);
		final JTextField ms = new JTextField("ms");
		final JTextField tips = new JTextField(
				"AA: ME: bin-15, ss-10, roiSize-1; NH: bin-20, ss-15, roiSize-1; GA: bin-240~300, ss-1~5,roiSize-10",
				80);

		tips.setHorizontalAlignment(SwingConstants.CENTER);

		bufferTime.setEnabled(false);
		ms.setEnabled(false);
		binThresh.setEnabled(false); // true可以编辑
		ssThresh.setEnabled(false); // true可以编辑
		mosaicL.setEnabled(false);
		percent.setEnabled(false); // true可以编辑
		tips.setEnabled(false);

		paraMeter.add(bufferTime);
		paraMeter.add(buffTime_manual);
		paraMeter.add(ms);
//		paraMeter.add(binThresh);
//		paraMeter.add(binarizationThreshold);
		paraMeter.add(ssThresh);
		paraMeter.add(spotSpotterThreshold);
		paraMeter.add(percent);
		paraMeter.add(mosaicL);
		paraMeter.add(mosaicLength_manual);
		// paraMeter.add(tips);

		// Panel3开关按钮*******************************************
		final JPanel switchPanel = new JPanel();
		switchPanel.setOpaque(true);
		// JButton select = new JButton("Select");
		final JButton start = new JButton("Manual");
		final JButton exit = new JButton("Exit");

		start.addActionListener(act_start);

		exit.addActionListener(act_exit);

		final JButton picMonitor = new JButton("Monitor");

		picMonitor.addActionListener(new ActionListener() {
			boolean ifPicMonitorON = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (!ifPicMonitorON) {
					openPicMonitor = false;
					ifPicMonitorON = true;
					jFrame.repaint();
					jFrame.remove(coverCopy);
					jFrame.add(main, BorderLayout.CENTER);

					jFrame.pack();
					System.out.println("Turn On Picture Monitor");
				} else {
					openPicMonitor = true;
					ifPicMonitorON = false;
					jFrame.repaint();
					jFrame.remove(main);
					jFrame.add(coverCopy, BorderLayout.CENTER);

					jFrame.pack();
					System.out.println("Turn Off Picture Monitor");
				}
			}
		});

		final JButton logMonitor = new JButton("Log");

		logMonitor.addActionListener(new ActionListener() {
			boolean ifLogMonitorON = true;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if (!ifLogMonitorON) {
					openLogMonitor = false;
					ifLogMonitorON = true;
					logContainer.repaint();
					// logContainer.add(consolePane, BorderLayout.EAST);
					jFrame.pack();
					System.out.println("Turn On Log Monitor");
				} else {
					openLogMonitor = true;
					ifLogMonitorON = false;
					logContainer.repaint();
					// logContainer.remove(consolePane);
					jFrame.pack();
					System.out.println("Turn Off Log Monitor");
				}

			}
		});

		// switchPanel.add(select);
		switchPanel.add(shortCutBox);
		switchPanel.add(shortCut);
		switchPanel.add(start);
		switchPanel.add(exit);
		switchPanel.add(engModeBox);
		switchPanel.add(engMode);
		// switchPanel.add(picMonitor);
		// switchPanel.add(logMonitor);

		// // Panel4当前监视路径*******************************************
		// JPanel currentPath = new JPanel();
		// currentPath.setOpaque(false);
		// JTextField path = new JTextField(monitorPath, 80);
		// currentPath.add(path);

		final JPanel overallCtrl = new JPanel();
		overallCtrl.setLayout(new GridLayout(3, 1));
		overallCtrl.add(switchPanel);
		overallCtrl.add(baseInfo);
		overallCtrl.add(paraMeter);
		// overallCtrl.add(tips);
		// overallCtrl.add(currentPath);

		// 原图窗口
		imageViewM = new JLabel();

		jFrame.add(overallCtrl, BorderLayout.SOUTH);
		jFrame.add(main, BorderLayout.CENTER);
		jFrame.add(imageViewM, BorderLayout.WEST);
		jFrame.add(consolePane, BorderLayout.EAST);

		// 总窗口设置************************************
		jFrame.pack();

		// jFrame.setUndecorated(false);//去掉窗体修饰,包括最大化按钮
		jFrame.setResizable(false); // 禁止改变窗体大小
		// jFrame.setLocationRelativeTo(null);
		jFrame.setLocation(20, 10);
		// jFrame.setBounds(50, 50, 1280, 768);
		jFrame.setVisible(true);

	}

	public static JScrollPane printOnLogMonitor() {
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
		} catch (final IOException e) {
			System.err.println("不能创建LoopedStreams：" + e);
			System.exit(1);
		}
		return new JScrollPane(consoleTextArea);
	}

	static int packcounter = 0;

	public static void showPicOnPost(Mat image) {
		if (openPicMonitor) {
			jFrame.repaint();
			loadedImage = Mat2BufferedImage.mat2BI(image);
			imageView.setIcon(new ImageIcon(loadedImage));
			if (packcounter == 0)
				jFrame.pack();
			packcounter++;
			// jF_overall.setLocationRelativeTo(null);
			// jF_overall.setLocation(650, 120);
			jFrame.setVisible(true);
		}
	}

	public static void showPicOnPre(Mat image) {
		if (openPicMonitor) {
			jFrame.repaint();
			loadedImage = Mat2BufferedImage.mat2BI(image);
			imageViewM.setIcon(new ImageIcon(loadedImage));
			if (packcounter == 0)
				jFrame.pack();
			packcounter++;
			// jF_overall.setLocationRelativeTo(null);
			// jF_overall.setLocation(650, 120);
			jFrame.setVisible(true);
		}
	}

	private static List<List<String>> list = IPReader.data();
	private static int macCount = list.size();
	private static String category;
	private static String ip;
	private static String autoPath;
	private static String iRCFVendor;

	private static void loadIP(int macIndex) {
		list = IPReader.data();
		category = list.get(macIndex).get(2);
		ip = list.get(macIndex).get(3);
		autoPath = list.get(macIndex).get(4);
	}

	private static String findPath() {
		String title = null;
		if (category.equals("NH"))
			title = "GRA-";
		else if (category.equals("ME"))
			title = "BB-";
		final String[] midFix = { "", "C", "D", "E" };
		final String postFix = "01\\";

		String finalPath = null;
		for (final String element : midFix) {
			final StringBuilder sbA = new StringBuilder(autoPath + "\\");
			final StringBuilder sbB = new StringBuilder(autoPath + "\\");
			final String aString = sbA.append(title).append(element).append("A").append(postFix).toString();
			final String bString = sbB.append(title).append(element).append("B").append(postFix).toString();

			final File pathA = new File(aString);
			final File pathB = new File(bString);
			if (pathA.exists()) {
				iRCFVendor = "AGC";
				finalPath = aString;
			} else if (pathB.exists()) {
				iRCFVendor = "PTOT";
				finalPath = bString;
			}
		}
		return finalPath;
	}
}
