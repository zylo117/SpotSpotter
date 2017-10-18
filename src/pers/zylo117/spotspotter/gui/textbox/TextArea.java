package pers.zylo117.spotspotter.gui.textbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextArea {
	static JFrame jf;
	static JPanel jpanel;
	static JButton jb1, jb2, jb3;
	static JTextArea jta = null;
	static JScrollPane jscrollPane;

	public static void JTextAreaDemo3() {
		jf = new JFrame("JTextArea����3");
		final Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());
		jta = new JTextArea(10, 15);
		jta.setTabSize(4);
		jta.setFont(new Font("�꿬��", Font.BOLD, 16));
		jta.setLineWrap(true);// �����Զ����й���
		jta.setWrapStyleWord(true);// ������в����ֹ���
		jta.setBackground(Color.pink);
		jscrollPane = new JScrollPane(jta);
		jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(1, 3));
		jb1 = new JButton("����");
		jb1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				jta.copy();
			}
		});
		jb2 = new JButton("ճ��");
		jb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				jta.paste();
			}
		});
		jb3 = new JButton("����");
		jb3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				jta.cut();
			}
		});
		jpanel.add(jb1);
		jpanel.add(jb2);
		jpanel.add(jb3);
		contentPane.add(jscrollPane, BorderLayout.CENTER);
		contentPane.add(jpanel, BorderLayout.SOUTH);
		jf.setSize(400, 300);
		jf.setLocation(400, 200);
		jf.setVisible(true);
		jf.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) throws IOException {
		// JTextAreaDemo3();
		// File f=new File("D:\\out.txt");
		// f.createNewFile();
		// FileOutputStream fileOutputStream = new FileOutputStream(f);
		// PrintStream printStream = new PrintStream(fileOutputStream);
		// System.setOut(printStream);
		// System.out.println("Ĭ�����������̨����һ�䣬��������ļ� out.txt");
	}
}
