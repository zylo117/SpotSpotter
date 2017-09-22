package pers.zylo117.spotspotter.mainprogram;

import java.util.ArrayList;
import java.util.List;

import pers.zylo117.spotspotter.fileprocessor.FIndexReader;
import pers.zylo117.spotspotter.gui.viewer.CentralControl;

public class BreakPoint {
	public static void continuous(List<String> oldlist) {
		oldlist = FIndexReader.getFIndex(CentralControl.monitorPath, false);
		List<String> newlist = FIndexReader.getFIndex(CentralControl.monitorPath, true);
		newlist.removeAll(oldlist);
		oldlist = newlist;
		oldlist = FIndexReader.indexProcess(CentralControl.monitorPath, oldlist);
	}
}
