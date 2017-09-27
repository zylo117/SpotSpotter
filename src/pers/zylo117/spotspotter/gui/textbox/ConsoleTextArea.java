package pers.zylo117.spotspotter.gui.textbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

public class ConsoleTextArea extends JTextArea {
	public ConsoleTextArea(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	public ConsoleTextArea() throws IOException {
		final LoopedStreams ls = new LoopedStreams();
		// �ض���System.out��System.err
		final PrintStream ps = new PrintStream(ls.getOutputStream());
		System.setOut(ps);
		System.setErr(ps);
		startConsoleReaderThread(ls.getInputStream());
	} // ConsoleTextArea()

	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		new Thread(new Runnable() {
			@Override
			public void run() {
				final StringBuffer sb = new StringBuffer();
				try {
					String s;
					final Document doc = getDocument();
					while ((s = br.readLine()) != null) {
						boolean caretAtEnd = false;
						caretAtEnd = getCaretPosition() == doc.getLength() ? true : false;
						sb.setLength(0);
						append(sb.append(s).append('\n').toString());
						if (caretAtEnd)
							setCaretPosition(doc.getLength());
					}
				} catch (final IOException e) {
					JOptionPane.showMessageDialog(null, "��BufferedReader��ȡ����" + e);
					System.exit(1);
				}
			}
		}).start();
	} // startConsoleReaderThread()
		// ����ʣ�ಿ�ֵĹ����ǽ��в���

	public static void main(String[] args) {
		final JFrame f = new JFrame("ConsoleTextArea����");
		ConsoleTextArea consoleTextArea = null;
		try {
			consoleTextArea = new ConsoleTextArea();
		} catch (final IOException e) {
			System.err.println("���ܴ���LoopedStreams��" + e);
			System.exit(1);
		}
		consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
		f.getContentPane().add(new JScrollPane(consoleTextArea), java.awt.BorderLayout.CENTER);
		f.setBounds(50, 50, 500, 500);
		f.setVisible(true);
		f.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				System.exit(0);
			}
		});

		// ��������д�����߳���
		// System.out��System.err���
		startWriterTestThread("д�����߳� #1", System.err, 920, 50);
		startWriterTestThread("д�����߳� #2", System.out, 500, 50);
		startWriterTestThread("д�����߳� #3", System.out, 200, 50);
		startWriterTestThread("д�����߳� #4", System.out, 1000, 50);
		startWriterTestThread("д�����߳� #5", System.err, 850, 50);
	}

	private static void startWriterTestThread(final String name, final PrintStream ps, final int delay,
			final int count) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= count; ++i) {
					ps.println("***" + name + ", hello !, i=" + i);
					try {
						Thread.sleep(delay);
					} catch (final InterruptedException e) {
					}
				}
			}
		}).start();
	}
}