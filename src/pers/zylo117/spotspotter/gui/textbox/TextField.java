package pers.zylo117.spotspotter.gui.textbox;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextField {
	static JFrame jf;
	static JPanel jp;
	static JTextField jtf1, jtf2, jtf3, jtf4;

	public static void JTextFieldDemo1() {

		jf = new JFrame("TextField案例");

		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());

		jp = new JPanel();

		jtf1 = new JTextField();
		jtf2 = new JTextField(10);
		jtf3 = new JTextField("指定文本内容");
		jtf4 = new JTextField("指定内容+指定长度(只读状态)", 30);

		jtf3.setEnabled(false); // true可以编辑
		jtf4.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 16)); // 字体，是否加粗、斜体，字号
		// 设置文本的水平对齐方式
		jtf4.setHorizontalAlignment(JTextField.CENTER);

		jp.add(jtf1);
		jp.add(jtf2);
		jp.add(jtf3);
		jp.add(jtf4);

		contentPane.add(jp);

		jf.pack();
		jf.setLocation(400, 200);
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		JTextFieldDemo1();
	}
}
