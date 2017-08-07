package pers.zylo117.spotspotter.viewer;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ImageFrame extends Frame {

	public ImageFrame(String input) {

		// 默认的窗体名称
		this.setTitle("ImageViewer");

		// 获得面板的实例
		ImagePanel panel = new ImagePanel(input);
		this.add(panel);
		this.addWindowListener(new WindowAdapter() {
			// 设置关闭
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 执行并构建窗体设定
		this.pack();
		this.setVisible(true);
	}

}