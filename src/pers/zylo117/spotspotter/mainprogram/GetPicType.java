package pers.zylo117.spotspotter.mainprogram;

import java.io.File;

public class GetPicType {
	public static String getPicType(String input) {
		File file = new File(input);
		String targetName = file.getName().substring(file.getName().lastIndexOf("_") + 1);
		String postfix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
}
