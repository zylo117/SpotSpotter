package pers.zylo117.fileprocessor;

import java.io.File;

public class FileDetecter {

	public static boolean judgeFileExists(String path) {
		File file = new File(path);
		while (true) {
			if (file.exists()) {
				System.out.println("File exists, OK 2 Proceed");
				break;
			}
			System.out.println("File doesn't exist, Can not Proceed");
		}
		return true;
	}
}
