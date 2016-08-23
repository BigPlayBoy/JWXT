package com.cui.tools;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Stack;


import com.cui.Bean.GradeEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cui.Bean.Grade;
import com.cui.Bean.Student;
import com.cui.mail.Mail;

/**
 * 建立一个存放了所有更新了成绩的学生的栈 搞定 然后查询学生的邮箱，成绩格式化，发送 发送邮件 需要知道对方的邮箱 新建一个发送邮箱的对象 发送的内容格式为
 * 尊敬的XXX:你有新的成绩出来了！！！！ 课程名： 成绩： 学分： 绩点：
 * 
 **/
public class EmailGrade {
	private static Logger log = LoggerFactory.getLogger(EmailGrade.class);

	public static void main(String[] args) {
		// 拼接发送的文本
		String emailContent = "晚上好 发送自java" + new Date();
		System.out.println(emailContent);

		// 获取邮箱地址
		// String emailAddress="cuilovexing@163.com";
		String emailAddress = "1151770629@qq.com";
		// 获取邮箱的配置文件
		Properties props = new Properties();// 新建一个配置对象
		try {
			props.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException" + e);
		} catch (IOException e) {
			log.error("配置文件mail.properties没有找到" + e);
		}
		
		String hostname = props.getProperty("mail.smtp.host");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		//SimpleMailSender mailsend = new SimpleMailSender(hostname, username, password);
		String subject = "你有新的成绩123456496";
		System.out.println("host:"+hostname+"\nusername:"+username+"\npassword:"+password+"\nemailAddress:"+emailAddress+"\n");
		Mail.send(hostname, username, emailAddress,  subject, emailContent, username, password);
		log.info("end");
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
		// System.out.println(sqlemail);
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
	}

	// 拼接成绩？//不用了
	public EmailGrade(String recipient, String content, String name, Grade grade) {

		this.recipient = recipient;
		this.content = content;
		this.name = name;
		this.grade = grade;
	}

	public static boolean sendEmail(Stack<Student> newGrade) {
		// 1.准备发送邮件所需要的东西
		/**
		 * smtp邮件服务器 , from发件人 , to收件人 , copyto抄送人（这个不需要） , subject主题 ,
		 * content内容 , username用户名 , password密码
		 */
		// 获取邮箱的配置文件
		Properties props = new Properties();// 新建一个配置对象
		try {
			props.load(new BufferedInputStream(new FileInputStream("src/com.cui.mail.properties")));
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException" + e);
		} catch (IOException e) {
			log.error("配置文件mail.properties没有找到" + e);
		}
		// 配置邮箱的属性
		String hostname = props.getProperty("mail.smtp.host");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String subject = "你有新的成绩";
		while (!newGrade.peek().grade.isEmpty()) {
			String studentcontent = "尊敬的";
			// 当有新增的成绩时 加工该成绩
			Student student = newGrade.pop();
			String name = student.getName();
			int StuID = student.getNumber();
			studentcontent = studentcontent + name + "学号:" + StuID + ",你有新的成绩出来了！！！";
			Stack<Grade> stackGrade = student.grade;
			String gradecontent = "\n";
			while (!stackGrade.isEmpty()) {
				// 这里加工成绩
				Grade grade = stackGrade.pop();
				String kechengming = grade.getKecheng();
				float chengji = grade.getChengji();
				float xuefen = grade.getXuefen();
				float jidian = grade.getJidian();
				gradecontent = gradecontent + "课程名：" + kechengming + "\n成绩:" + chengji + "\n学分:" + xuefen + "\n绩点:"
						+ jidian + " \r\n<br>";
			}
			// 拼接发送的文本
			String emailContent = studentcontent + gradecontent;
			// System.out.println(emailContent);

			// 获取邮箱地址
			String emailAddress = EmailGrade.getEmail(StuID);
			// System.out.println(emailAddress);

			// String recipient=emailAddress;
			// 发送邮件
			// smtp, from, to, copyto, subject, content, username, password
			Mail.send(hostname, username, emailAddress,  subject, emailContent, username, password);
			// System.out.println("发送成功");
			// 每发送一次 过5秒钟 再次发送
			// wait five minutes to show jobs
			try {
				Thread.sleep(5L * 1000L);
			} catch (InterruptedException e) {
				//
			}
			log.info("邮件发送成功" + name);
		}
		return true;
	}
	//08 23增加
	public static boolean sendEmailWithGrade(Stack<GradeEntity> gradeEntityStack) {
		// 1.准备发送邮件所需要的东西
		/**
		 * smtp邮件服务器 , from发件人 , to收件人 , copyto抄送人（这个不需要） , subject主题 ,
		 * content内容 , username用户名 , password密码
		 */
		// 获取邮箱的配置文件
		Properties props = new Properties();// 新建一个配置对象
		try {
			props.load(new BufferedInputStream(new FileInputStream("src/mail.properties")));
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException" + e);
		} catch (IOException e) {
			log.error("配置文件mail.properties没有找到" + e);
		}
		// 配置邮箱的属性
		String hostname = props.getProperty("mail.smtp.host");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String subject = "你有新的成绩";
		while (!gradeEntityStack.isEmpty()) {
			String studentcontent = "尊敬的";
			// 当有新增的成绩时 加工该成绩
//			Student student = newGrade.pop();
			GradeEntity gradeEntity=gradeEntityStack.pop();
			String name = gradeEntity.getSid().toString();
			studentcontent = studentcontent + name + "学号:,你有新的成绩出来了！！！";
			String gradecontent = "\n";
				String kechengming = gradeEntity.getKecheng();
				double chengji = gradeEntity.getCehngji();
				double xuefen = gradeEntity.getXuefen();
				gradecontent = gradecontent + "课程名：" + kechengming + "\n成绩:" + chengji + "\n学分:" + xuefen + "\r\n<br>";
			// 拼接发送的文本
			String emailContent = studentcontent + gradecontent;
			 log.info(emailContent);
			// 获取邮箱地址
			String emailAddress = gradeEntity.getStudentEntity().geteMail();
			// 发送邮件
			// smtp, from, to, copyto, subject, content, username, password
			Mail.send(hostname, username, emailAddress,  subject, emailContent, username, password);
			// System.out.println("发送成功");
			// 每发送一次 过5秒钟 再次发送
			// wait five minutes to show jobs
			try {
				Thread.sleep(5L * 1000L);
			} catch (InterruptedException e) {
				log.error(e.toString());
			}
			log.info("邮件发送成功" + name);
		}
		return true;
	}
}
