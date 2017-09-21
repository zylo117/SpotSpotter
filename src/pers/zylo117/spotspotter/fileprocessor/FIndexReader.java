package pers.zylo117.spotspotter.fileprocessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import pers.zylo117.spotspotter.mainprogram.AlgoList;
import pers.zylo117.spotspotter.toolbox.Time;

public class FIndexReader {
	public static List<String> getFIndex(String path, boolean ifNew) {
		File file = new File(System.getProperty("user.dir") + "/tmpIndex.dat");
		Time.getTime();
		String todayFIndex = path + "\\" + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\" + "findex.dat";
		File fIndex = new File(todayFIndex);
		File onLoad;
		if(!file.exists() || ifNew){	
			onLoad = fIndex;
		}
		else {
			onLoad = file;	
		}
		String content = "";

		try {
			FileInputStream in = new FileInputStream(onLoad);
			// size 为字串的长度 ，这里一次性读完
			int size = in.available();
			byte[] buffer = new byte[size];
			in.read(buffer);
			in.close();
			content = new String(buffer, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
        String[] indexList = content.split(System.lineSeparator());
        List<String> list = Arrays.asList(indexList);
        list.toArray();
		return list;
		
	}
	
//	public static List<Map<String, Boolean>> fIndex(String path){
//		List<Map<String, Boolean>> list = new ArrayList<>();
//		List<String> indexList = getFIndex(path);
//        for (int i = 0; i < indexList.size(); i++) {
////            System.out.println(indexList[i]);
//            Map<String, Boolean> indexStatus = new HashMap<>();
//            indexStatus.put(indexList.get(i), false);
//            list.add(indexStatus);
//        }
//
//        return list;
//	}
	
	public static List<String> indexProcess(String path) {
		Time.getTime();
		String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		List<String> list = getFIndex(path, false);
		List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			newlist.add(list.get(i));
		}

		return newlist;
	}
	
	public static List<String> indexProcess(String path, List<String> list) {
		Time.getTime();
		String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			newlist.add(list.get(i));
			outputIndex(newlist.get(i));
		}

		return newlist;
	}

	public static void outputIndex(String content) {
		File file = new File(System.getProperty("user.dir") + "/tmpIndex.dat");
		try {
			if (!file.exists())
				file.createNewFile();
			// 然后创建一个文件输出流
			FileOutputStream fos;
			fos = new FileOutputStream(file, true);
			// 然后可以把一个对象用.toString()方法转换成字符串。
			// 然后再用.getBytes()转换成字符数组。
			content += System.lineSeparator();
			byte[] bytes = content.getBytes();
			// 写入文件：
			fos.write(bytes);
			fos.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
//		PrintWriter pfp;
//		try {
//			pfp = new PrintWriter(file);
//			pfp.print(str);
//			pfp.close();
//		} catch (FileNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		String path = "\\\\43.98.145.236\\WorkLog\\MTInsp\\Image\\ALL\\GRA-DB01";
		System.out.println(getFIndex(path, false));
		getFIndex(path,true);
//		fIndex(path);
		
//        String str ="1001"+"\n"+"1002"+"\n"+"1003"+"\n"+"asdf";
//        //用回车键来分隔几个元素
//        String[] ss = str.split("\n");
//        for (int i = 0; i < ss.length; i++) {
//            System.out.println(ss[i]);
//        }
	}

}