package pers.zylo117.spotspotter.toolbox;

import java.io.File;
import java.io.IOException;

public class GetRootPath {
	public static String getRootPath() throws IOException {
		// ��ȡ��Ŀ·��
        File directory = new File("");// ����Ϊ��
        String courseFile = directory.getCanonicalPath();
        return courseFile;
	}
}
