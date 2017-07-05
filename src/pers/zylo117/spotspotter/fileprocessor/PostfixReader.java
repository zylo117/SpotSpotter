package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;

public class PostfixReader {
	public static String getPostfix(File file) {
		String fileName = file.getName();
		String postfix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return postfix;
	}
}
