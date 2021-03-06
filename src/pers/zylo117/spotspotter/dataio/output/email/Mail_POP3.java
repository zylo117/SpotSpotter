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
	public static void main(String[] args) {
		try {
			final String host = "smtp.qq.com";// 这是QQ邮箱的smtp服务器地址
			final String port = "25"; // 端口号
			/*
			 * Properties是一个属性对象，用来创建Session对象
			 */
			final Properties props = new Properties();
			props.setProperty("mail.smtp.host", host);
			props.setProperty("mail.smtp.port", port);
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.ssl.enable", "false");// "true"
			props.setProperty("mail.smtp.connectiontimeout", "5000");
			final String user = "******@qq.com";// 用户名
			final String pwd = "******";// 密码
			/*
			 * Session类定义了一个基本的邮件对话。
			 */
			final Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// 登录用户名密码
					return new PasswordAuthentication(user, pwd);
				}
			});
			session.setDebug(true);
			/*
			 * Transport类用来发送邮件。 传入参数smtp，transport将自动按照smtp协议发送邮件。
			 */
			final Transport transport = session.getTransport("smtp");// "smtps"
			transport.connect(host, user, pwd);
			/*
			 * Message对象用来储存实际发送的电子邮件信息
			 */
			final MimeMessage message = new MimeMessage(session);
			message.setSubject("邮件标题");
			// 消息发送者接收者设置(发件地址，昵称)，收件人看到的昵称是这里设定的
			message.setFrom(new InternetAddress(user, "二师兄"));
			message.addRecipients(Message.RecipientType.TO, new InternetAddress[] {
					// 消息接收者(收件地址，昵称)
					// 不过这个昵称貌似没有看到效果
					new InternetAddress("510172916@qq.com", "大师兄"), });
			message.saveChanges();

			// 设置邮件内容及编码格式
			// 后一个参数可以不指定编码，如"text/plain"，但是将不能显示中文字符
			message.setContent("邮件内容..", "text/plain;charset=UTF-8");
			// 发送
			// transport.send(message);
			Transport.send(message);
			transport.close();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
