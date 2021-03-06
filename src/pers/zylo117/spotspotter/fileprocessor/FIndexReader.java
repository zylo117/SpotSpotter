package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pers.zylo117.spotspotter.gui.viewer.CentralControl;
import pers.zylo117.spotspotter.mainprogram.AlgoList;
import pers.zylo117.spotspotter.mainprogram.MainLoader;
import pers.zylo117.spotspotter.toolbox.Time;

public class FIndexReader {
	public static List<String> getFIndex(boolean ifNew) {
		final File oldFileIndex = MainLoader.oldFileIndex;
		final File fIndex = MainLoader.fIndex;
		File onLoad = null;
		if (!oldFileIndex.exists() || ifNew || FileCreateTime.ifOutOfDate(oldFileIndex)) {
			onLoad = fIndex;
			if (FileCreateTime.ifOutOfDate(oldFileIndex))
				oldFileIndex.delete();
		} else {
			onLoad = oldFileIndex;
		}
		String content = "";

		try {
			final FileInputStream in = new FileInputStream(onLoad);
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

		final List<String> list = new ArrayList<String>();
		for (final String element : indexList) {
			list.add(element);
		}
		return list;

	}

	// public static List<Map<String, Boolean>> fIndex(String path){
	// List<Map<String, Boolean>> list = new ArrayList<>();
	// List<String> indexList = getFIndex(path);
	// for (int i = 0; i < indexList.size(); i++) {
	//// System.out.println(indexList[i]);
	// Map<String, Boolean> indexStatus = new HashMap<>();
	// indexStatus.put(indexList.get(i), false);
	// list.add(indexStatus);
	// }
	//
	// return list;
	// }

	public static List<String> indexProcess(String path) {
		Time.getTime();
		final String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		final List<String> list = getFIndex(false);
		final List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			newlist.add(list.get(i));
		}

		return newlist;
	}

	public static void indexProcess(String path, List<String> list) {
		Time.getTime();
		final String currentPath = CentralControl.monitorPath;
		final String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		final List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			if (!CentralControl.monitorPath.isEmpty() && !CentralControl.monitorPath.equals(path)) {
				// final File file = new File(System.getProperty("user.dir") + "/tmpIndex.dat");
				// file.delete();
				break;
			}

			// 用于防错，同时用于重启计算
			if (currentPath != CentralControl.monitorPath)
				break;

			newlist.add(list.get(i));
			outputIndex(newlist.get(i));
		}
	}

	public static void indexProcess(File listFile) {
		final String path = listFile.getParent();
		String content = null;
		try {
			final FileInputStream in = new FileInputStream(listFile);
			// size 为字串的长度 ，这里一次性读完
			final int size = in.available();
			final byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			content = new String(buffer, "UTF-8");
		} catch (final IOException e) {
			content = null;
		}

		final String[] indexList = content.split(System.lineSeparator());

		for (final String element : indexList) {
			AlgoList.panda(path + "/" + element);
		}
	}

	public static void outputIndex(String content) {
		final File file = MainLoader.oldFileIndex;
		try {
			if (!file.exists())
				file.createNewFile();
			// 然后创建一个文件输出流
			FileOutputStream fos;
			fos = new FileOutputStream(file, true);
			// 然后可以把一个对象用.toString()方法转换成字符串。
			// 然后再用.getBytes()转换成字符数组。
			content += System.lineSeparator();
			final byte[] bytes = content.getBytes();
			// 写入文件：
			fos.write(bytes);
			fos.close();
		} catch (final IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// PrintWriter pfp;
		// try {
		// pfp = new PrintWriter(file);
		// pfp.print(str);
		// pfp.close();
		// } catch (FileNotFoundException e) {
		// // TODO 自动生成的 catch 块
		// e.printStackTrace();
		// }
	}

	public static void main(String[] args) {
		final String path = "\\\\43.98.145.236\\WorkLog\\MTInsp\\Image\\ALL\\GRA-DB01";
		// System.out.println(getFIndex(path, false));
		// getFIndex(path,true).remove(getFIndex(path, false));
		final List<String> list1 = new ArrayList<>();
		list1.add("aaaa");
		list1.add("bbbb");
		list1.add("cccc");

		final List<String> list2 = new ArrayList();
		list2.add("cccc");
		list2.add("dddd");
		list2.add("eeee");

		list1.removeAll(list2);

		System.out.println(list1);
		// fIndex(path);

		// String str ="1001"+"\n"+"1002"+"\n"+"1003"+"\n"+"asdf";
		// //用回车键来分隔几个元素
		// String[] ss = str.split("\n");
		// for (int i = 0; i < ss.length; i++) {
		// System.out.println(ss[i]);
		// }
	}

}