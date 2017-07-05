package fileListener;

import java.io.File;

public class Filedetecter {

	public static boolean judgeFileExists(String path) {
		File file = new File(path);
		while(true) {
		if(file.exists()){
			System.out.println("OK 2 Proceed");
			break;
		}
	}
		return true;
	}
}
