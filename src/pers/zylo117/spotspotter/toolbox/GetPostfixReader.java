package pers.zylo117.spotspotter.toolbox;

import java.io.File;

public class GetPostfixReader {
	public static String getPostfix(File file) {
		String fileName = file.getName();
		String postfix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return postfix;
	}
}
