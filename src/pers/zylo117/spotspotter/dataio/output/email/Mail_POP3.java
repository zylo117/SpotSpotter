package pers.zylo117.spotspotter.dataio.output.email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail_POP3 {
	 public static void main(String[] args){
	        try {
	            String host = "mailex.0-film.com";//����QQ�����smtp��������ַ
	            String port = "110"; //�˿ں�
	            /*
	            *Properties��һ�����Զ�����������Session����
	            */
	            Properties props = new Properties();
	            props.setProperty("mail.smtp.host", host);
	            props.setProperty("mail.smtp.port", port);
	            props.setProperty("mail.smtp.auth", "true");
	            props.setProperty("mail.smtp.ssl.enable", "false");//"true"
	            props.setProperty("mail.smtp.connectiontimeout", "5000");
	            final String user = "******@qq.com";//�û���
	            final String pwd = "******";//����
	            /*
	            *Session�ඨ����һ���������ʼ��Ի���
	            */
	            Session session = Session.getInstance(props, new Authenticator() {
	                @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    //��¼�û�������
	                    return new PasswordAuthentication(user,pwd);
	                }
	            });
	            session.setDebug(true);
	            /*
	            *Transport�����������ʼ���
	            *�������smtp��transport���Զ�����smtpЭ�鷢���ʼ���
	            */
	            Transport transport = session.getTransport("smtp");//"smtps"
	            transport.connect(host,user,pwd);
	            /*
	            *Message������������ʵ�ʷ��͵ĵ����ʼ���Ϣ
	            */
	            MimeMessage message = new MimeMessage(session);
	            message.setSubject("�ʼ�����");
	            //��Ϣ�����߽���������(������ַ���ǳ�)���ռ��˿������ǳ��������趨��
	            message.setFrom(new InternetAddress(user,"��ʦ��"));
	            message.addRecipients(Message.RecipientType.TO,new InternetAddress[]{
	                //��Ϣ������(�ռ���ַ���ǳ�)
	                //��������ǳ�ò��û�п���Ч��
	                    new InternetAddress("510172916@qq.com","��ʦ��"),
	            });
	            message.saveChanges();

	            //�����ʼ����ݼ������ʽ
	            //��һ���������Բ�ָ�����룬��"text/plain"�����ǽ�������ʾ�����ַ�
	            message.setContent("�ʼ�����..", "text/plain;charset=UTF-8");
	            //����
	            //transport.send(message);
	            Transport.send(message);
	            transport.close();
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
}
