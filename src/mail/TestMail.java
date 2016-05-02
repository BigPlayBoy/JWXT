package mail;


public class TestMail {

	public static void main(String[] args) throws Exception {
		// Properties props = new Properties();//新建一个配置对象
		// props.load(new BufferedInputStream(new
		// FileInputStream("src/mail.properties")));
		// //props.put("mail.smtp.host", "smtp.163.com");//设置host地址
		// //props.put("mail.smtp.auth", "true");//是否验证
		// //基本的邮件会话
		// Session session = Session.getInstance(props);
		// //构造信息体
		// MimeMessage message = new MimeMessage(session);
		// //发件地址
		// Address address = new
		// InternetAddress("cuilovexing@163.com");//发送邮件的账号
		// message.setFrom(address);
		// //收件地址
		// Address toAddress = new InternetAddress("1151770629@qq.com");//
		// message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
		//
		// //主题
		// message.setSubject("Hello world");
		// //正文
		// message.setText("Hello world");
		//
		// message.saveChanges(); // implicit with send()
		// //Exception in thread "main" javax.mail.NoSuchProviderException: smtp
		// session.setDebug(true);
		// Transport transport = session.getTransport("smtp");
		// transport.connect("smtp.163.com", "cuilovexing@163.com",
		// "1994cuiminghui");
		// //发送
		// transport.sendMessage(message, message.getAllRecipients());
		// transport.close();
		String smtp = "smtp.163.com";
		String from = "cuilovexing@163.com";
		String to = "1151770629@qq.com";
		String copyto = "抄送人";
		String subject = "邮件主题";
		String content = "邮件内容";
		String username = "cuilovexing@163.com";
		String password = "1994cuiminghui";
		// String filename = "附件路径，如：F:\\笔记<a>\\struts2</a>与mvc.txt";
		Mail.send(smtp, from, to, copyto, subject, content, username, password);

	}
}