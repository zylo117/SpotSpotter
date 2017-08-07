package pers.zylo117.spotspotter.viewer;

import java.awt.Frame;

public class ImageViewerAPI {
	 public static void main(String[] args) { 
		 String input = "../image/1.jpg";
	        Frame frame=new ImageFrame(input);  
			 input = "../image/2.jpg";
		        frame=new ImageFrame(input);  
		}
}
