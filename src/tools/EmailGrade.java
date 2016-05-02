package tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import info.Grade;
import info.Student;
import mail.SimpleMailSender;

/**
 * 建立一个存放了所有更新了成绩的学生的栈 搞定 然后查询学生的邮箱，成绩格式化，发送 发送邮件 需要知道对方的邮箱 新建一个发送邮箱的对象 发送的内容格式为
 * 尊敬的XXX:你有新的成绩出来了！！！！ 课程名： 成绩： 学分： 绩点：
 * 
 **/
public class EmailGrade {
	public static void main(String[] args) {
		//拼接发送的文本
		String emailContent="晚上好 发送自java"+new Date();
		System.out.println(emailContent);
		
		//获取邮箱地址
//		String emailAddress="cuilovexing@163.com";
		String emailAddress="1151770629@qq.com";
		
		Properties props = new Properties();//新建一个配置对象
	       try {
			props.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("文件未找到");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("IO异常？？？？？");
		}
		String	hostname=props.getProperty("mail.smtp.host");
		String	username=props.getProperty("InternetAddress");
		String	password=props.getProperty("password");
	       SimpleMailSender mailsend = new SimpleMailSender(hostname,username,password);
		String	recipient=emailAddress;
		String	content=emailContent;
		String subject="你有新的成绩123456496";
		try {
			//发送邮件
			mailsend.send(recipient, subject, content);
			System.out.println("发送成功");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("邮箱地址错误？？");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			System.out.println("邮件错误？？？？？");
		}
		System.out.println("end");
	}
	// SimpleMailSender sendemail=new SimpleMailSender();

	String recipient;
	String content = "尊敬的name:你有新的成绩出来了！！！\n学号:xuehao\n课程名:kechengming\n成绩:chengji\n学分:xuefen\n绩点:jidian\n";
	String name;
	Grade grade;
	// 将所有的学生信息压入栈中 然后一个一个弹出来；
	Stack<Student> studentList = new Stack<>();

	// 先获取用户的邮箱地址
	public static String getEmail(int number) {
		String email = null;
		String sqlemail = "select Email from Student where StuID=" + String.valueOf(number);
		//System.out.println(sqlemail);
		// 查询该学生的邮箱
		email = JDBCTools.QueryEmail(sqlemail);
		return email;
	}

	public static String getName(int number) {
		String name = null;
		String sqlName = "select Name from Student where StuID=" + number;
		System.out.println(sqlName);
		// 查询该学生的邮箱
		name = JDBCTools.QueryEmail(sqlName);
		return name;
	}

	public EmailGrade() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 拼接成绩？//不用了
	public EmailGrade(String recipient, String content, String name, Grade grade) {
		super();

		this.recipient = recipient;
		this.content = content;
		this.name = name;
		this.grade = grade;
	}

	public static boolean sendEmail(Stack<Student> newGrade) {
		//1.准备发送邮件所需要的东西
		/**
		 * smtp邮件服务器
		 * , from发件人
		 * , to收件人
		 * , copyto抄送人（这个不需要）
		 * , subject主题
		 * , content内容
		 * , username用户名
		 * , password密码
		 */
		while (!newGrade.peek().grade.isEmpty()) {
			String	studentcontent="尊敬的";
			// 当有新增的成绩时 加工该成绩
			Student student=newGrade.pop();
			String	name=student.getName();
			int	StuID=student.getNumber();
			studentcontent=studentcontent+name+"学号:"+StuID+",你有新的成绩出来了！！！";
			Stack<Grade> stackGrade=student.grade;
			String gradecontent="\n";
			while(!stackGrade.isEmpty()){
				//这里加工成绩
				Grade grade=stackGrade.pop();
				String	kechengming=grade.getKecheng();
				float chengji=grade.getChengji();
				float	xuefen=grade.getXuefen();
				float	jidian=grade.getJidian();
				gradecontent=gradecontent+"课程名："+kechengming+"\n成绩:"+chengji+"\n学分:"+xuefen+"\n绩点:"+jidian+" \r\n<br>";
			}
			//拼接发送的文本
			String emailContent=studentcontent+gradecontent;
			//System.out.println(emailContent);
			
			//获取邮箱地址
			String emailAddress=EmailGrade.getEmail(StuID);
			//System.out.println(emailAddress);
			
			Properties props = new Properties();//新建一个配置对象
		       try {
				props.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("文件未找到");
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				System.out.println("IO异常？？？？？");
			}
			String	hostname=props.getProperty("mail.smtp.host");
			String	username=props.getProperty("InternetAddress");
			String	password=props.getProperty("password");
		       SimpleMailSender mailsend = new SimpleMailSender(hostname,username,password);
			String	recipient=emailAddress;
			String	content=emailContent;
			String subject="你有新的成绩";
			try {
				//发送邮件
				mailsend.send(recipient, subject, content);
				//System.out.println("发送成功");
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("邮箱地址错误？？");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				System.out.println("邮件错误？？？？？");
			}
			//每发送一次 过5秒钟 再次发送
			try {
			      // wait five minutes to show jobs
			      Thread.sleep(5L * 1000L);
			      // executing...
			    } catch (Exception e) {
			      //
			    }
		}
		
		return true;
	}

}
