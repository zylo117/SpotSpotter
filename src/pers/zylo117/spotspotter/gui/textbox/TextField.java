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

		jf = new JFrame("TextField����");

		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());

		jp = new JPanel();

		jtf1 = new JTextField();
		jtf2 = new JTextField(10);
		jtf3 = new JTextField("ָ���ı�����");
		jtf4 = new JTextField("ָ������+ָ������(ֻ��״̬)", 30);

		jtf3.setEnabled(false); // true���Ա༭
		jtf4.setFont(new Font("����", Font.BOLD | Font.ITALIC, 16)); // ���壬�Ƿ�Ӵ֡�б�壬�ֺ�
		// �����ı���ˮƽ���뷽ʽ
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
