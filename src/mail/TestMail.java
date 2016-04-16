package mail;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestMail {

   public static void main(String[] args) throws Exception {
       Properties props = new Properties();//新建一个配置对象
       props.put("mail.smtp.host", "smtp.163.com");//设置host地址
       props.put("mail.smtp.auth", "true");//是否验证
       //基本的邮件会话
       Session session = Session.getInstance(props);
       //构造信息体
       MimeMessage message = new MimeMessage(session); 
       //发件地址
       Address address = new InternetAddress("cuilovexing@163.com");//发送邮件的账号
       message.setFrom(address);
       //收件地址
       Address toAddress = new InternetAddress("1151770629@qq.com");//
       message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
       
       //主题
       message.setSubject("Hello world");
       //正文
       message.setText("Hello world");

       message.saveChanges(); // implicit with send()
       //Exception in thread "main" javax.mail.NoSuchProviderException: smtp
       session.setDebug(true);
       Transport transport = session.getTransport("smtp");
       transport.connect("smtp.163.com", "cuilovexing@163.com", "1994cuiminghui");
       //发送
       transport.sendMessage(message, message.getAllRecipients());
       transport.close(); 

   }
}