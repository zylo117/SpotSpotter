package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;

public class FileOperation {

	public static void createDir(String destDirName) {
		final File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			// System.out.println("Target dir already existed");
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("Succeeded to create dir" + destDirName);
		}
	}

	public static boolean isFileExists(String path, boolean print) {
		final File file = new File(path);
		while (true) {
			if (file.exists()) {
				if (print)
					System.out.println("File exists, OK 2 Proceed");
				return true;
			} else {
				if (print)
					System.out.println("File doesn't exist, Can not Proceed");
				return false;
			}
		}

	}

	public static boolean isFileNameValid(String fileName) {
		if (fileName == null || fileName.length() > 255 || ifFilenameContainsSpaceKey(fileName)) {
			System.out.println("Filename is not valid");
			return false;
		} else
			return fileName.matches(
					"[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
	}

	public static boolean ifFilenameContainsSpaceKey(String fileName) {
		final CharSequence spacekey1 = "%20";
		final CharSequence spacekey2 = " ";
		if (fileName.contains(spacekey1) || fileName.contains(spacekey2)) {
			return true;
		}
		return false;
	}

	public static String RemoveSpaceKeyFromFilename(String fileName) {
		final CharSequence spacekey1 = "%20";
		final CharSequence spacekey2 = " ";
		String result;
		if (fileName.contains(spacekey1) || fileName.contains(spacekey2)) {
			result = fileName.replace(" ", "");
			return result;
		}
		return fileName;
	}

	// public static void main(String[] args) {
	// String aString = "024504(1)811_glue - 副本.jpg";
	// aString = RemoveSpaceKeyFromFilename(aString);
	// System.out.println(aString);
	// }
}
