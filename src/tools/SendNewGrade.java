package tools;

import java.awt.List;
import java.util.Stack;

import info.Grade;
import mail.Mail;
import mail.SimpleMailSender;

/**
 * 发送邮件 需要知道对方的邮箱
 * 新建一个发送邮箱的对象  
 * 发送的内容格式为 尊敬的XXX:你有新的成绩出来了！！！！
 * 课程名：
 * 成绩：
 * 学分：
 * 绩点：
 * 
 **/
public class SendNewGrade {
	//SimpleMailSender sendemail=new SimpleMailSender();
	
	String recipient;
	String content="尊敬的name:你有新的成绩出来了！！！\n学号:xuehao\n课程名:kechengming\n成绩:cehngji\n学分:xuefen\n绩点:jidian\n";
	String name;
	Grade	grade;
	//先获取用户的邮箱地址
	public String getemail(String number){
		String email=null;
		String sqlemail="select Email from Student where StuID="+number;
		System.out.println(sqlemail);
		email=JDBCTools.executeQueryEmail(sqlemail);
		return email;
	}
	//拼接成绩？//不用了
	public SendNewGrade(String recipient, String content, String name, Grade grade) {
		super();
		
		this.recipient = recipient;
		this.content = content;
		this.name = name;
		this.grade = grade;
	}
	public	boolean sendEmail(Stack<Grade> newGrade){
		//List StuID=new List();
		//newGrade栈中包含了所有新增加成绩的人的信息。。。。
		//是不是应该把栈里放新增加成绩的学生？？
		while(!newGrade.isEmpty()){
			//将新的成绩提出来 进行加工
			Grade grade=newGrade.pop();
			String StuID=String.valueOf(grade.getNumber());//将学号转换为字符串
			String kechengming=grade.getKecheng();
			float	chengji=grade.getChengji();
			float	xuefen=grade.getXuefen();
			float	jidian=grade.getJidian();
			
		}
		
		return true;
	}
	
}
