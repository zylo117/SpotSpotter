package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;

public class FileDetecter {

	public static boolean judgeFileExists(String path) {
		File file = new File(path);
		while(true) {
		if(file.exists()){
			System.out.println("File exists, OK 2 Proceed");
			break;
		}
	}
		return true;
	}
}
