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

		jf = new JFrame("TextField����");

		final Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());

		jp = new JPanel();

		jtf1 = new JTextField();
		jtf2 = new JTextField(10);
		jtf3 = new JTextField("ָ���ı�����");
		jtf4 = new JTextField("ָ������+ָ������(ֻ��״̬)", 30);

		jtf3.setEnabled(false); // true���Ա༭
		jtf4.setFont(new Font("����", Font.BOLD | Font.ITALIC, 16)); // ���壬�Ƿ�Ӵ֡�б�壬�ֺ�
		// �����ı���ˮƽ���뷽ʽ
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
		final JLabel nameLab = new JLabel("�����û�����:");
		final JLabel noedLab = new JLabel("���ɱ༭�ı� ");
		noed.setEnabled(false);
		name.setColumns(30);
		noed.setColumns(30);
		f.setLayout(new GridLayout(3, 2));// ���ò��ֹ�����
		f.add(nameLab);
		f.add(name);
		f.add(noedLab);
		f.add(noed);
		f.setSize(300, 100);
		f.setLocation(300, 200);
		f.setVisible(true);

	}
}
