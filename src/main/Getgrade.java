package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Stack;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.Grade;
import info.IdAndPasswd;
import info.Student;
import tools.EmailGrade;
import tools.JDBCTools;
import tools.SQLTools;
import tools.Tools;

public class Getgrade implements Job {
	private static Logger _log = LoggerFactory.getLogger(Getgrade.class);

	public static void main(String[] args) {
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = JDBCTools.QueryPasswd(null);// 得到学号和密码
		//
		Stack<Student> newGrade=new Stack<>();//用来存放更新了成绩的学生
		
		System.out.println("获取学号和密码成功");
		_log.info("获取学号和密码成功" + new Date());
		while (!passwd.isEmpty()) {
			Student stu=new Student();
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			passwd.pop();//拿到学号和密码之后 将学生信息弹出
			// 获取成绩页面
			System.out.println("获取网页信息");
			_log.info("获取网页信息");
			String GradePage = Tools.getGradePage(xuehao, upass);
			// 拿到成绩页面，提取出学生信息和成绩
			System.out.println("已获取网页信息，正获取学生信息");
			_log.info("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);//返回的是一个学生的所有成绩
			// 存入数据库
			// System.out.println(student);
			System.out.println("存入数据库");
			_log.info("存入数据库");
//			 这里应当返回一个包含了新增成绩的栈 然后 发送出去
//			 额 又需要在数据库中 添加邮箱了。。。
			stu=JDBCTools.saveStudent(student);
			newGrade.push(stu);// 已经考虑是否有重复了
			//判断是否有新成绩增加
			if(!newGrade.isEmpty()){
				//增加了新的成绩
				_log.info("有新成绩增加");
				EmailGrade.sendEmail(newGrade);
				System.out.println("发送成功");
			}
		}
		System.out.println("end???");
	}

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		JobKey jobKey = context.getJobDetail().getKey();
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = Tools.getStuIdandPasswd();// 得到学号和密码
		_log.info("获取学号和密码成功");
		_log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		while (!passwd.isEmpty()) {
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			passwd.pop();
			// 获取成绩页面
			_log.info("获取网页信息");
			String GradePage = Tools.getGradePage(xuehao, upass);
			// 拿到成绩页面，提取出学生信息和成绩
			_log.info("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);
			// 存入数据库
			// System.out.println(student);
			_log.info("存入数据库");
			SQLTools.saveStudent(student);// 已经考虑是否有重复了
		}
	}

}