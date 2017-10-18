package pers.zylo117.spotspotter.toolbox;

import java.io.File;
import java.io.IOException;

public class GetRootPath {
	public static String getRootPath() throws IOException {
		// 获取项目路径
		final File directory = new File("");// 参数为空
		final String courseFile = directory.getCanonicalPath();
		return courseFile;
	}
}
