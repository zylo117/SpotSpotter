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
	public static List<String> getFIndex(String path, boolean ifNew) {
		final File oldFileIndex = MainLoader.oldFileIndex;
		final File fIndex = MainLoader.fIndex;
		File onLoad = null;
		if(!oldFileIndex.exists() || ifNew || FileCreateTime.ifOutOfDate(oldFileIndex)){	
			onLoad = fIndex;
			if(FileCreateTime.ifOutOfDate(oldFileIndex))
				oldFileIndex.delete();
		}
		else {
			onLoad = oldFileIndex;	
		}
		String content = "";

		try {
			final FileInputStream in = new FileInputStream(onLoad);
			// size Ϊ�ִ��ĳ��� ������һ���Զ���
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
        for(int i = 0; i<indexList.length;i++) {
        	list.add(indexList[i]);
        }
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
		final String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		final List<String> list = getFIndex(path, false);
		final List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			newlist.add(list.get(i));
		}

		return newlist;
	}
	
	public static void indexProcess(String path, List<String> list) {
		Time.getTime();
		final String todayFIndex = path + Time.year + "\\" + Time.strMonth + "\\" + Time.strDay + "\\";
		final List<String> newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AlgoList.panda(todayFIndex + list.get(i));
			if (!CentralControl.monitorPath.isEmpty() && !CentralControl.monitorPath.equals(path)) {
//				final File file = new File(System.getProperty("user.dir") + "/tmpIndex.dat");
//				file.delete();
				break;
			}
			newlist.add(list.get(i));
			outputIndex(newlist.get(i));
		}
	}

	public static void outputIndex(String content) {
		final File file = MainLoader.oldFileIndex;
		try {
			if (!file.exists())
				file.createNewFile();
			// Ȼ�󴴽�һ���ļ������
			FileOutputStream fos;
			fos = new FileOutputStream(file, true);
			// Ȼ����԰�һ��������.toString()����ת�����ַ�����
			// Ȼ������.getBytes()ת�����ַ����顣
			content += System.lineSeparator();
			final byte[] bytes = content.getBytes();
			// д���ļ���
			fos.write(bytes);
			fos.close();
		} catch (final IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
//		PrintWriter pfp;
//		try {
//			pfp = new PrintWriter(file);
//			pfp.print(str);
//			pfp.close();
//		} catch (FileNotFoundException e) {
//			// TODO �Զ����ɵ� catch ��
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		final String path = "\\\\43.98.145.236\\WorkLog\\MTInsp\\Image\\ALL\\GRA-DB01";
//		System.out.println(getFIndex(path, false));
//		getFIndex(path,true).remove(getFIndex(path, false));
		final List<String> list1 =new ArrayList<>();
		list1.add("aaaa");
		list1.add("bbbb");
		list1.add("cccc");

		final List<String> list2 =new ArrayList();
		list2.add("cccc");
		list2.add("dddd");
		list2.add("eeee");

		
		list1.removeAll(list2);
		
		System.out.println(list1);
//		fIndex(path);
		
//        String str ="1001"+"\n"+"1002"+"\n"+"1003"+"\n"+"asdf";
//        //�ûس������ָ�����Ԫ��
//        String[] ss = str.split("\n");
//        for (int i = 0; i < ss.length; i++) {
//            System.out.println(ss[i]);
//        }
	}

}