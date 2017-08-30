package pers.zylo117.spotspotter.dataoutput.actualprocessdata;

import java.io.File;

import pers.zylo117.spotspotter.pictureprocess.Picture;

public class PythagorasData {
	public static String getType(Picture pic) {
		String targetName = pic.fileName.substring(pic.fileName.lastIndexOf("_") + 1);
		String postfix = pic.fileName.substring(pic.fileName.lastIndexOf(".") + 1);
		int postfixLength = postfix.length();// 得到后缀名长度
		String targetNameWithputPostfix = targetName.substring(0, targetName.length() - postfixLength - 1);// 得到目标名。去掉了后缀
		return targetNameWithputPostfix;
	}

	public static String getHour(Picture pic) {
		return pic.fileName.substring(0, 2);
	}

	public static String getMinute(Picture pic) {
		return pic.fileName.substring(2, 4);
	}

	public static String getSecond(Picture pic) {
		return pic.fileName.substring(4, 6);
	}
}
