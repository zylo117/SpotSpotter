package pers.zylo117.spotspotter.patternrecognition.facialrecognition;
 
import java.awt.EventQueue;
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import pers.zylo117.spotspotter.toolbox.Mat2BufferedImage;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
public class CameraShot {
     
    static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
 
    private JFrame frame;
    static JLabel label;
    static int flag=0;//�ྲ̬���������ڿ��ư��°�ť�� ֹͣ����ͷ�Ķ�ȡ
 
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                try {
                    CameraShot window = new CameraShot();
                    window.frame.setVisible(true);
                     
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //���ǵĲ���
        VideoCapture camera=new VideoCapture();//����Opencv�е���Ƶ��׽����
        camera.open(0);//open�����е�0����ǰ�����������Ϊ0������ͷ�������ļ�����ж������ͷ����ôһ��1,2,3����
        if(!camera.isOpened()){//isOpened���������ж�����ͷ�����Ƿ�ɹ�
            System.out.println("Camera Error");//�������ͷ����ʧ�ܣ����������Ϣ
        }
        else{
            Mat frame=new Mat();//����һ�����֡
            while(flag==0){
                camera.read(frame);//read������ȡ����ͷ�ĵ�ǰ֡
                label.setIcon(new ImageIcon(Mat2BufferedImage.mat2BI(frame)));//ת��ͼ���ʽ�����
                try {
                    Thread.sleep(100);//�߳���ͣ100ms
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
 
    /**
     * Create the application.
     */
    public CameraShot() {
        initialize();
    }
 
    /**
     * Initialize the contents of the frame. 
     */
    private void initialize(){
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
         
        JButton btnNewButton = new JButton("\u62CD\u7167");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                flag=1;//��̬��������Ϊ1���Ӷ����°�ťʱ��ֹͣ����ͷ�ĵ���
            }
        });
        btnNewButton.setBounds(33, 13, 113, 27);
        frame.getContentPane().add(btnNewButton);
         
        label = new JLabel("");
        label.setBounds(0, 0, 800, 450);
        frame.getContentPane().add(label);  
    }
}
