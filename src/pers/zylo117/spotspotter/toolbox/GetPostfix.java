package pers.zylo117.spotspotter.toolbox;

import java.io.File;

public class GetPostfix {
	public static String fromFilepath(String input) {
		File file = new File(input);
		String postfix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		return postfix;
	}
	
	public static String fromFilename(String input) {
		String postfix = input.substring(input.lastIndexOf(".") + 1);
		return postfix;
	}
}
