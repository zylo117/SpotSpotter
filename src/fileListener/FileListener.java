package fileListener;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import pictureProcess.Joblist;
import pictureProcess.PicProcess;
import pictureProcess.ROInaive;

public class FileListener {

	public static String filename;
	
	public static void Autoscript() throws IOException, InterruptedException {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		// 监视文件创建/删除/修改,仅支持JAVA 1.7及以上版本
		Paths.get(PicProcess.original).register(watcher, 
				StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);

		while (true) {
			WatchKey key = watcher.take();
			for (WatchEvent<?> event : key.pollEvents()) {
				// 文件创建/删除/修改时通知
				//System.out.println(event.context() + " comes to " + event.kind());
				Object eventcontext = event.context();
				filename = obj2String.o2s(eventcontext);
				Object eventkind = event.kind();
				String eventkindstr = obj2String.o2s(eventkind);

				if (eventkindstr.equals("ENTRY_CREATE")){
					System.out.println(filename  + " Created");
					System.out.println("Ready 2 be spitted");
					Joblist.joblist();
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				break;
			}
		}
	}
}