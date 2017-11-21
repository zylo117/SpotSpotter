package pers.zylo117.spotspotter.mainprogram;

import java.io.IOException;
import java.util.List;

import pers.zylo117.spotspotter.fileprocessor.FIndexReader;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;

public class BreakPoint {
	public static void continuous(List<String> oldlist) {
		oldlist = FIndexReader.getFIndex(false);
		final List<String> newlist = FIndexReader.getFIndex(true);
		if (!newlist.isEmpty()) {
			newlist.removeAll(oldlist);
			if (!newlist.isEmpty()) {
				oldlist = newlist;
				FIndexReader.indexProcess(CentralControl.monitorPath, oldlist);
			} else {
				if (!MainLoader.oldFileIndex.exists())
					try {
						MainLoader.oldFileIndex.createNewFile();
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				System.out.println("Empty List");
			}
		}
	}
}
