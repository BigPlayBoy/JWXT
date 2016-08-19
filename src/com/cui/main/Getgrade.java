package com.cui.main;

import java.util.Stack;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cui.Bean.IdAndPasswd;
import com.cui.Bean.Student;
import com.cui.tools.EmailGrade;
import com.cui.tools.JDBCTools;
import com.cui.tools.Tools;

public class Getgrade implements Job {
	private static Logger _log = LoggerFactory.getLogger(Getgrade.class);

	public static void main(String[] args) {
		
		long start=System.currentTimeMillis();
		getGrade();
		long end=System.currentTimeMillis();
		_log.info("total run time"+(end-start));
		
	}

	public static void getGrade() {
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = JDBCTools.QueryPasswd(null);// 得到学号和密码
		Stack<Student> newGrade = new Stack<>();// 用来存放更新了成绩的学生
		while (!passwd.isEmpty()) {
			// System.out.println("获取学号和密码成功");

			Student stu = new Student();
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			_log.info("success！get SId：" + xuehao);
			passwd.pop();// 拿到学号和密码之后 将学生信息弹出
			// 获取成绩页面
			String GradePage = Tools.getGradePage(xuehao, upass);
//			if (Tools.savepage(GradePage, xuehao)) {
//				System.out.println("保存网页成功");
//			}
			// 拿到成绩页面，提取出学生信息和成绩
			_log.info("success get page,get student com.cui.Bean");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);// 返回的是一个学生的所有成绩
			// 存入数据库
			_log.info("update database");
			// 这里应当返回一个包含了新增成绩的栈 然后 发送出去
			// 额 又需要在数据库中 添加邮箱了。。。
			stu = JDBCTools.saveStudent(student);// 返回的是该学生的所有新增成绩
			newGrade.push(stu);// 已经考虑是否有重复了
		}
		// 判断是否有新成绩增加
		// System.out.println(newGrade);
		if (!newGrade.peek().grade.isEmpty()) {
			// 增加了新的成绩
			_log.info("new Grade");
			EmailGrade.sendEmail(newGrade);
			_log.info("send email Success");
		} else {
			_log.info("no new Grade");
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long start=System.currentTimeMillis();
		getGrade();
		long end=System.currentTimeMillis();
		_log.info("total run time"+(end-start));
	}

}