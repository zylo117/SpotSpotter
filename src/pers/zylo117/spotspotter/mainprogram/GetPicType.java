package pers.zylo117.spotspotter.mainprogram;

import java.io.File;

import pers.zylo117.spotspotter.pictureprocess.Picture;

public class GetPicType {
	public static String getPicTypeFromPath(String input) {
		final File file = new File(input);
		final String targetName = file.getName().substring(file.getName().lastIndexOf("_") + 1);
		final String postfix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		final int postfixLength = postfix.length();// 得到后缀名长度
		final String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
	
	public static String getPicTypeFromFilename(String input) {
		final String targetName = input.substring(input.lastIndexOf("_") + 1);
		final String postfix = input.substring(input.lastIndexOf(".") + 1);
		final int postfixLength = postfix.length();// 得到后缀名长度
		final String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
	
	public static String getPicTypeFromPic(Picture pic) {
		final String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		final String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		final int postfixLength = postfix.length();// 得到后缀名长度
		final String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}
}
