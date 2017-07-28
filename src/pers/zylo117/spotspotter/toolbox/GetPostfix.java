package pers.zylo117.spotspotter.toolbox;

import java.io.File;

public class GetPostfix {
	public static String getPostfix(String input) {
		File file = new File(input);
		String postfix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		return postfix;
	}
}
