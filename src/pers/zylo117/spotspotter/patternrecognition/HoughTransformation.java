package pers.zylo117.spotspotter.patternrecognition;
//package pers.zylo117.spotspotter.patternrecognition;
//
//import java.awt.image.BufferedImage;
//
//import com.process.blur.study.AbstractBufferedImageOp;
//
//public class HoughTransformation extends AbstractBufferedImageOp {
//	public final static double PI_VALUE = Math.PI;
//	private int hough_space = 500;
//	private int[] hough_1d;
//	private int[][] hough_2d;
//	private int width;
//	private int height;
//	
//	private float threshold;
//	private float scale;
//	private float offset;
//	
//	public HoughTransformation() {
//		// default hough transform parameters
//		//	scale = 1.0f;
//		//	offset = 0.0f;
//		threshold = 0.5f;
//		scale = 1.0f;
//		offset = 0.0f;
//	}
//	
//	public void setHoughSpace(int space) {
//		this.hough_space = space;
//	}
//	
//	public float getThreshold() {
//		return threshold;
//	}
//
//	public void setThreshold(float threshold) {
//		this.threshold = threshold;
//	}
//
//	public float getScale() {
//		return scale;
//	}
//
//	public void setScale(float scale) {
//		this.scale = scale;
//	}
//
//	public float getOffset() {
//		return offset;
//	}
//
//	public void setOffset(float offset) {
//		this.offset = offset;
//	}
//
//	@Override
//	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
//		width = src.getWidth();
//        height = src.getHeight();
//
//        if ( dest == null )
//            dest = createCompatibleDestImage( src, null );
//
//        int[] inPixels = new int[width*height];
//        int[] outPixels = new int[width*height];
//        getRGB( src, 0, 0, width, height, inPixels );
//        houghTransform(inPixels, outPixels);
//        setRGB( dest, 0, 0, width, height, outPixels );
//        return dest;
//	}
//
//	private void houghTransform(int[] inPixels, int[] outPixels) {
//        // prepare for hough transform
//	    int centerX = width / 2;
//	    int centerY = height / 2;
//	    double hough_interval = PI_VALUE/(double)hough_space;
//	    
//	    int max = Math.max(width, height);
//	    int max_length = (int)(Math.sqrt(2.0D) * max);
//	    hough_1d = new int[2 * hough_space * max_length];
//	    
//	    // define temp hough 2D array and initialize the hough 2D
//	    hough_2d = new int[hough_space][2*max_length];
//	    for(int i=0; i<hough_space; i++) {
//	    	for(int j=0; j<2*max_length; j++) {
//	    		hough_2d[i][j] = 0;
//	    	}
//	    }
//	    
//	    // start hough transform now....
//	    int[][] image_2d = convert1Dto2D(inPixels);
//	    for (int row = 0; row < height; row++) {
//	    	for (int col = 0; col < width; col++) {
//	        	int p = image_2d[row][col] & 0xff;
//	        	if(p == 0) continue; // which means background color
//	        	
//	        	// since we does not know the theta angle and r value, 
//	        	// we have to calculate all hough space for each pixel point
//	        	// then we got the max possible theta and r pair.
//	        	// r = x * cos(theta) + y * sin(theta)
//	        	for(int cell=0; cell < hough_space; cell++ ) {
//	        		max = (int)((col - centerX) * Math.cos(cell * hough_interval) + (row - centerY) * Math.sin(cell * hough_interval));
//	        		max += max_length; // start from zero, not (-max_length)
//	        		if (max < 0 || (max >= 2 * max_length)) {// make sure r did not out of scope[0, 2*max_lenght]
//	                    continue;
//	                }
//	        		hough_2d[cell][max] +=1;
//	        	}
//	        }
//	    }
//	    
//		// find the max hough value
//		int max_hough = 0;
//		for(int i=0; i<hough_space; i++) {
//			for(int j=0; j<2*max_length; j++) {
//				hough_1d[(i + j * hough_space)] = hough_2d[i][j];
//				if(hough_2d[i][j] > max_hough) {
//					max_hough = hough_2d[i][j];
//				}
//			}
//		}
//		System.out.println("MAX HOUGH VALUE = " + max_hough);
//		
//		// transfer back to image pixels space from hough parameter space
//		int hough_threshold = (int)(threshold * max_hough);
//	    for(int row = 0; row < hough_space; row++) {
//	    	for(int col = 0; col < 2*max_length; col++) {
//	    		if(hough_2d[row][col] < hough_threshold) // discard it
//	    			continue;
//	    		int hough_value = hough_2d[row][col];
//	    		boolean isLine = true;
//	    		for(int i=-1; i<2; i++) {
//	    			for(int j=-1; j<2; j++) {
//	    				if(i != 0 || j != 0) {
//    		              int yf = row + i;
//    		              int xf = col + j;
//    		              if(xf < 0) continue;
//    		              if(xf < 2*max_length) {
//    		            	  if (yf < 0) {
//    		            		  yf += hough_space;
//    		            	  }
//    		                  if (yf >= hough_space) {
//    		                	  yf -= hough_space;
//    		                  }
//    		                  if(hough_2d[yf][xf] <= hough_value) {
//    		                	  continue;
//    		                  }
//    		                  isLine = false;
//    		                  break;
//    		              }
//	    				}
//	    			}
//	    		}
//	    		if(!isLine) continue;
//	    		
//	    		// transform back to pixel data now...
//	            double dy = Math.sin(row * hough_interval);
//	            double dx = Math.cos(row * hough_interval);
//	            if ((row <= hough_space / 4) || (row >= 3 * hough_space / 4)) {
//	                for (int subrow = 0; subrow < height; ++subrow) {
//	                  int subcol = (int)((col - max_length - ((subrow - centerY) * dy)) / dx) + centerX;
//	                  if ((subcol < width) && (subcol >= 0)) {
//	                	  image_2d[subrow][subcol] = -16776961;
//	                  }
//	                }
//	              } else {
//	                for (int subcol = 0; subcol < width; ++subcol) {
//	                  int subrow = (int)((col - max_length - ((subcol - centerX) * dx)) / dy) + centerY;
//	                  if ((subrow < height) && (subrow >= 0)) {
//	                	  image_2d[subrow][subcol] = -16776961;
//	                  }
//	                }
//	              }
//	    	}
//	    }
//	    
//	    // convert to hough 1D and return result
//	    for (int i = 0; i < this.hough_1d.length; i++)
//	    {
//	      int value = clamp((int)(scale * this.hough_1d[i] + offset)); // scale always equals 1
//	      this.hough_1d[i] = (0xFF000000 | value + (value << 16) + (value << 8));
//	    }
//	    
//	    // convert to image 1D and return
//	    for (int row = 0; row < height; row++) {
//	    	for (int col = 0; col < width; col++) {
//	        	outPixels[(col + row * width)] = image_2d[row][col];
//	        }
//	    }
//	}
//	
//	public BufferedImage getHoughImage() {
//		BufferedImage houghImage = new BufferedImage(hough_2d[0].length, hough_space, BufferedImage.TYPE_4BYTE_ABGR);
//		setRGB(houghImage, 0, 0, hough_2d[0].length, hough_space, hough_1d);
//		return houghImage;
//	}
//	
//	public static int clamp(int value) {
//	      if (value < 0)
//	    	  value = 0;
//	      else if (value > 255) {
//	    	  value = 255;
//	      }
//	      return value;
//	}
//	
//	private int[][] convert1Dto2D(int[] pixels) {
//		int[][] image_2d = new int[height][width];
//		int index = 0;
//		for(int row = 0; row < height; row++) {
//			for(int col = 0; col < width; col++) {
//				index = row * width + col;
//				image_2d[row][col] = pixels[index];
//			}
//		}
//		return image_2d;
//	}
//
//}