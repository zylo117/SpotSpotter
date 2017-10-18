package pers.zylo117.spotspotter.dataio.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IPReader {
	public static List<List<String>> data() {
		final String path = System.getProperty("user.dir") + "\\MachineIP.csv";
		final File file = new File(path);
		final List<List<String>> list = new ArrayList<>();
		if (file.exists()) {
			String content = null;
			try {
				final FileInputStream in = new FileInputStream(file);
				// size 为字串的长度 ，这里一次性读完
				final int size = in.available();
				final byte[] buffer = new byte[size];
				in.read(buffer);
				in.close();
				content = new String(buffer, "UTF-8");
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				return null;
			}
			final String[] indexList = content.split(System.lineSeparator());
			for (int i = 1; i < indexList.length; i++) {
				final List<String> data_line = Arrays.asList(indexList[i].split(","));
				list.add(data_line);
			}
			// System.out.println(list);
			return list;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		data();
	}
}
