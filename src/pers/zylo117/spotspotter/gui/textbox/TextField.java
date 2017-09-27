package pers.zylo117.spotspotter.gui.textbox;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TextField {
	static JFrame jf;
	static JPanel jp;
	static JTextField jtf1, jtf2, jtf3, jtf4;

	public static void JTextFieldDemo1() {

		jf = new JFrame("TextField案例");

		final Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());

		jp = new JPanel();

		jtf1 = new JTextField();
		jtf2 = new JTextField(10);
		jtf3 = new JTextField("指定文本内容");
		jtf4 = new JTextField("指定内容+指定长度(只读状态)", 30);

		jtf3.setEnabled(false); // true可以编辑
		jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
		// 设置文本的水平对齐方式
		jtf4.setHorizontalAlignment(SwingConstants.CENTER);

		jp.add(jtf1);
		jp.add(jtf2);
		jp.add(jtf3);
		jp.add(jtf4);

		contentPane.add(jp);

		jf.pack();
		jf.setLocation(400, 200);
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		JTextFieldDemo1();
		final JFrame f = new JFrame("Welcome To Earth!");
		final JTextField name = new JTextField(30);
		final JTextField noed = new JTextField("HJW", 10);
		final JLabel nameLab = new JLabel("输入用户姓名:");
		final JLabel noedLab = new JLabel("不可编辑文本 ");
		noed.setEnabled(false);
		name.setColumns(30);
		noed.setColumns(30);
		f.setLayout(new GridLayout(3, 2));// 设置布局管理器
		f.add(nameLab);
		f.add(name);
		f.add(noedLab);
		f.add(noed);
		f.setSize(300, 100);
		f.setLocation(300, 200);
		f.setVisible(true);

	}
}
