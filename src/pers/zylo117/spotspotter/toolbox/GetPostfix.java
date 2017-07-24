package pers.zylo117.spotspotter.toolbox;

import java.io.File;

public class GetPostfix {
	public static String getPostfix(String input) {
		File file = new File(input);
		String fileName = file.getName();
		String postfix = fileName.substring(fileName.lastIndexOf(".") + 1);
		return postfix;
	}
}
