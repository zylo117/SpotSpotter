package pers.zylo117.spotspotter.fileprocessor;

public class Obj2String {

	public static String o2s(Object obj) {
		return (obj == null) ? "null" : obj.toString();
	}

}
