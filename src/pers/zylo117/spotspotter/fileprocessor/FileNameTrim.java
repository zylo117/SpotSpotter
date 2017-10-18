package pers.zylo117.spotspotter.fileprocessor;

public class FileNameTrim {

	public static String getStatus() {
		return FileListener.fileName.substring(19, 22);
	}

	public static String getYear() {
		return FileListener.fileName.substring(0, 4);
	}

	public static String getMonth() {
		return FileListener.fileName.substring(4, 6);
	}

	public static String getDay() {
		return FileListener.fileName.substring(6, 8);
	}

}
