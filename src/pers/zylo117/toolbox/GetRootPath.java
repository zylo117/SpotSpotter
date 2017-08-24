package pers.zylo117.toolbox;

import java.io.File;
import java.io.IOException;

public class GetRootPath {
	public static String getRootPath() throws IOException {
		// 获取项目路径
        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        return courseFile;
	}
}
