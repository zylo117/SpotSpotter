package pers.zylo117.spotspotter.mainprogram;

import java.io.File;

import pers.zylo117.spotspotter.pictureprocess.Picture;

public class GetPicType {
	public static String getPicTypeFromPath(String input) {
		File file = new File(input);
		String targetName = file.getName().substring(file.getName().lastIndexOf("_") + 1);
		String postfix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
	
	public static String getPicTypeFromFilename(String input) {
		String targetName = input.substring(input.lastIndexOf("_") + 1);
		String postfix = input.substring(input.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
	
	public static String getPicTypeFromPic(Picture pic) {
		String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
}
