package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;

import org.springframework.context.support.StaticApplicationContext;

public class FileDetecter {

	public static boolean isFileExists(String path) {
		File file = new File(path);
		while (true) {
			if (file.exists()) {
				System.out.println("File exists, OK 2 Proceed");
				return true;
			}
			else {
				System.out.println("File doesn't exist, Can not Proceed");
				return false;
			}
		}
		
	}
	
	public static boolean isFileNameValid(String fileName) {
		if (fileName == null || fileName.length() > 255 || ifFilenameContainsSpaceKey(fileName)) {
			System.out.println("Filename is not valid");
			return false;
		}
		else
			return fileName.matches(
					"[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
	}

	public static boolean ifFilenameContainsSpaceKey(String fileName) {
		CharSequence spacekey1 = "%20";
		CharSequence spacekey2 = " ";
		if(fileName.contains(spacekey1) ||fileName.contains(spacekey2)) {
			return true;
		}
		return false;
	}
	
	public static String RemoveSpaceKeyFromFilename(String fileName) {
		CharSequence spacekey1 = "%20";
		CharSequence spacekey2 = " ";
		String result;
		if(fileName.contains(spacekey1) ||fileName.contains(spacekey2)) {
			result = fileName.replace(" ","");
			return result;
		}
		return fileName;
	}
	
//	public static void main(String[] args) {
//		String aString = "024504(1)811_glue - ¸±±¾.jpg";
//		aString = RemoveSpaceKeyFromFilename(aString);
//		System.out.println(aString);
//	}
}
