package pers.zylo117.spotspotter.gui.textbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class LoopedStreams {
	private final PipedOutputStream pipedOS = new PipedOutputStream();
	private boolean keepRunning = true;
	private final ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
		@Override
		public void close() {
			keepRunning = false;
			try {
				super.close();
				pipedOS.close();
			} catch (final IOException e) {
				// 记载缺陷或其他处置责罚
				// 为庞杂计，此处我们直接停止
				System.exit(1);
			}
		}
	};
	private final PipedInputStream pipedIS = new PipedInputStream() {
		@Override
		public void close() {
			keepRunning = false;
			try {
				super.close();
			} catch (final IOException e) {
				// 记载缺陷或其他处置责罚
				// 为庞杂计，此处我们直接停止
				System.exit(1);
			}
		}
	};

	public LoopedStreams() throws IOException {
		pipedOS.connect(pipedIS);
		startByteArrayReaderThread();
	} // LoopedStreams()

	public InputStream getInputStream() {
		return pipedIS;
	} // getInputStream()

	public OutputStream getOutputStream() {
		return byteArrayOS;
	} // getOutputStream()

	private void startByteArrayReaderThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (keepRunning) {
					// 搜检流里面的字节数
					if (byteArrayOS.size() > 0) {
						byte[] buffer = null;
						synchronized (byteArrayOS) {
							buffer = byteArrayOS.toByteArray();
							byteArrayOS.reset(); // 祛除缓冲区
						}
						try {
							// 把提取到的数据发送给PipedOutputStream
							pipedOS.write(buffer, 0, buffer.length);
						} catch (final IOException e) {
							// 记载缺陷或其他处置责罚
							// 为庞杂计，此处我们直接停止
							System.exit(1);
						}
					} else // 没罕有据可用，线程进入睡眠形态
						try {
							// 每隔1秒检查ByteArrayOutputStream搜检新数据
							Thread.sleep(1000);
						} catch (final InterruptedException e) {
						}
				}
			}
		}).start();
	} // startByteArrayReaderThread()
} // LoopedStreams