package pers.zylo117.spotspotter.mainprogram;

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
			} else
				System.out.println("Empty List");
		}
	}
}
