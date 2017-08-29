package pers.zylo117.spotspotter.fileprocessor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import pers.zylo117.spotspotter.mainprogram.AlgoList;
import pers.zylo117.spotspotter.mainprogram.PathManagement;
import pers.zylo117.spotspotter.toolbox.Obj2String;

public class FileListener {

	public static String filename;
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
				filename = Obj2String.o2s(eventcontext);
				Object eventkind = event.kind();
				String eventkindstr = Obj2String.o2s(eventkind);

				if (eventkindstr.equals("ENTRY_CREATE")) {
					System.out.println(filename + " Created");
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
}