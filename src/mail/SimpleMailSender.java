package mail;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 简单邮件发送器，可单发，群发。
 * 
 * @author MZULE
 * 
 */
public class SimpleMailSender {

	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();
	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	/**
	 * 邮箱session
	 */
	private transient Session session;

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 *            SMTP邮件服务器地址
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String smtpHostName, final String username, final String password) {
		init(username, password, smtpHostName);
	}

	/**
	 * 初始化构造函数，因为 啥都没有
	 */
	public SimpleMailSender() {
		super();
	}

	/**
	 * 初始化邮件发送器
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)，并以此解析SMTP服务器地址
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String username, final String password) {
		// 通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
		final String smtpHostName = "smtp." + username.split("@")[1];
		init(username, password, smtpHostName);
	}

	/**
	 * 初始化
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            密码
	 * @param smtpHostName
	 *            SMTP主机地址
	 */
	private void init(String username, String password, String smtpHostName) {
		// 初始化props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, String subject, Object content) throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	// public void send(String recipient, SimpleMail mail) throws
	// AddressException, MessagingException {
	// send(recipient, mail.getSubject(), mail.getContent());
	// }

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param mail
	 *            邮件对象
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws AddressException
	 * @throws MessagingException
	 */
	// public void send(List<String> recipients, SimpleMail mail) throws
	// AddressException, MessagingException {
	// send(recipients, mail.getSubject(), mail.getContent());
	// }

	public static void main(String[] args) throws FileNotFoundException, IOException, AddressException, MessagingException {
		// Properties props = new Properties();
		// props.put("mail.smtp.host", "smtp.163.com");
		// props.put("mail.from", "cuilovexing@163.com");
		// props.put("mail.smtp.auth", "true");
		// Session session = Session.getInstance(props, null);
		//
		// try {
		// MimeMessage msg = new MimeMessage(session);
		// msg.setFrom();
		// msg.setRecipients(Message.RecipientType.TO, "1151770629@qq.com");
		// msg.setSubject("JavaMail hello world example");
		// msg.setSentDate(new Date());
		// msg.setText("Hello, world!\n");
		//// msg.set
		// //Transport.connect("smtp.163.com","cuilovexing@163.com","1994cuiminghui");
		// Transport transport=session.getTransport("smtp");
		// transport.connect("smtp.163.com", "cuilovexing@163.com",
		// "1994cuiminghui");
		// transport.sendMessage(msg, msg.getAllRecipients());
		// //transport.send(msg);
		// transport.close();
		//
		// System.out.println("发送成功！");
		// } catch (MessagingException mex) {
		// System.out.println("send failed, exception: " + mex);
		// }
		Properties props = new Properties();//新建一个配置对象
	       props.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
		String	hostname=props.getProperty("mail.smtp.host");
		String	username=props.getProperty("InternetAddress");
		String	password=props.getProperty("password");
	       SimpleMailSender mailsend = new SimpleMailSender(hostname,username,password);
		String	recipient="1151770629@qq.com";
		String	content="第一封邮件 Hello World！";
		String subject="这里填写主题";
		mailsend.send(recipient, subject, content);
		//mailsend.
	}
}
