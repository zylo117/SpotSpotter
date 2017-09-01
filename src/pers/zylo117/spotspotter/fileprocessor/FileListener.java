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
		// 监视文件创建/删除/修改,仅支持JAVA 1.7及以上版本
		Paths.get(PathManagement.monitorPath).register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

		while (true) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				// 文件创建/删除/修改时通知
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
		// 获取文件系统的WatchService对象
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
				if (file2.isDirectory()) {// 下一级目录
					fList.addLast(file2);
					// 依次注册子目录
					Paths.get(file2.getAbsolutePath()).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
				}
			}
		}

		while (true) {
			// 获取下一个文件改动事件
			WatchKey key = watchService.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				// 文件创建/删除/修改时通知
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
			// 重设WatchKey
			boolean valid = key.reset();
			// 如果重设失败，退出监听
			if (!valid) {
				break;
			}
		}
	}
	
//	public static void main(String[] args) throws IOException, InterruptedException {
//		autoDeepScan(2,"D:\\EpoxyInsp\\EW4\\");
//	}
}