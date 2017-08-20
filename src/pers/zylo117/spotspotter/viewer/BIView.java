package pers.zylo117.spotspotter.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class BIView{
	 /**
     * Display Mat image
     *
     * @param image
     * @param windowName
     */
    public static void imshow(BufferedImage image, String windowName){
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame jFrame = new JFrame(windowName);
        JLabel imageView = new JLabel();
        final JScrollPane imageScrollPane = new JScrollPane(imageView);
        imageScrollPane.setPreferredSize(new Dimension(500, 500));  // set window size
        jFrame.add(imageScrollPane, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image loadedImage = image;
        imageView.setIcon(new ImageIcon(loadedImage));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
