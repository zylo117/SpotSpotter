package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.LinkedList;

import pers.zylo117.spotspotter.mainprogram.AlgoList;
import pers.zylo117.spotspotter.mainprogram.PathManagement;
import pers.zylo117.spotspotter.toolbox.Obj2String;

public class FileListener {

	public static String fileName;
	public static String filePath;
	public static boolean ifReset;
	public static WatchService watcher;

	public static void Autoscript(int index) throws IOException, InterruptedException {
		watcher = FileSystems.getDefault().newWatchService();
		// �����ļ�����/ɾ��/�޸�,��֧��JAVA 1.7�����ϰ汾
		Paths.get(PathManagement.monitorPath).register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

		while (true) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				// �ļ�����/ɾ��/�޸�ʱ֪ͨ
				// System.out.println(event.context() + " comes to " + event.kind());
				Object eventcontext = event.context();
				fileName = Obj2String.o2s(eventcontext);
				Object eventkind = event.kind();
				String eventkindstr = Obj2String.o2s(eventkind);

				if (eventkindstr.equals("ENTRY_CREATE")) {
					System.out.println(fileName + " Created");
					System.out.println("Ready 2 be analysed");

					switch (index) {
					case 1:
						AlgoList.godzilla();
						break;

					case 2:
						AlgoList.pythagoras_G();
						break;

					default:
						AlgoList.pythagoras_G();
						break;
					}

				}
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
			if (ifReset) {
				break;
			}
		}
	}

	public static void autoDeepScan(int index) throws IOException, InterruptedException {
		// ��ȡ�ļ�ϵͳ��WatchService����
		WatchService watchService = FileSystems.getDefault().newWatchService();
		Paths.get(PathManagement.monitorPath).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

		File file = new File(PathManagement.monitorPath);
		LinkedList<File> fList = new LinkedList<File>();
		fList.addLast(file);
		while (fList.size() > 0) {
			File f = fList.removeFirst();
			if (f.listFiles() == null)
				continue;
			for (File file2 : f.listFiles()) {
				if (file2.isDirectory()) {// ��һ��Ŀ¼
					fList.addLast(file2);
					// ����ע����Ŀ¼
					Paths.get(file2.getAbsolutePath()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
				}
			}
		}

		while (true) {
			// ��ȡ��һ���ļ��Ķ��¼�
			WatchKey key = watchService.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				// �ļ�����/ɾ��/�޸�ʱ֪ͨ
				// System.out.println(event.context() + " comes to " + event.kind());
				Object eventcontext = event.context();
				fileName = Obj2String.o2s(eventcontext);
				Object eventkind = event.kind();
				String eventkindstr = Obj2String.o2s(eventkind);
				
				if (eventkindstr.equals("ENTRY_CREATE")) {
					System.out.println(fileName + " Created");
					System.out.println(key.watchable() + " Modified");
					Object path = (Object) key.watchable();
					filePath = Obj2String.o2s(path);
					System.out.println("Ready 2 be analysed");

					switch (index) {
					case 1:
						AlgoList.godzilla();
						break;

					case 2:
						AlgoList.pythagoras_G();
						break;

					default:
						AlgoList.pythagoras_G();
						break;
					}

				}
			}
			// ����WatchKey
			boolean valid = key.reset();
			// �������ʧ�ܣ��˳�����
			if (!valid) {
				break;
			}
		}
	}
	
//	public static void main(String[] args) throws IOException, InterruptedException {
//		autoDeepScan(2,"D:\\EpoxyInsp\\EW4\\");
//	}
}