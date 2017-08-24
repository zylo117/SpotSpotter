package pers.zylo117.fileprocessor;

import pers.zylo117.fileprocessor.FileListener;

public class FileNameTrim {
	
	public static String getStatus(){
		return FileListener.filename.substring(19,22);
	}
	
	public static String getYear(){
		return FileListener.filename.substring(0,4);
		}
	
	public static String getMonth(){
		return FileListener.filename.substring(4,6);
		}
	
	public static String getDay(){
		return FileListener.filename.substring(6,8);
		}
	
}
