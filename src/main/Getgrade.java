package main;

import java.util.Stack;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.IdAndPasswd;
import info.Student;
import tools.EmailGrade;
import tools.JDBCTools;
import tools.Tools;

public class Getgrade implements Job {
	private static Logger _log = LoggerFactory.getLogger(Getgrade.class);

	public static void main(String[] args) {
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		Stack<IdAndPasswd> passwd = JDBCTools.QueryPasswd(null);// 得到学号和密码
		Stack<Student> newGrade = new Stack<>();// 用来存放更新了成绩的学生
		while (!passwd.isEmpty()) {
			// System.out.println("获取学号和密码成功");

			Student stu = new Student();
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			_log.info("获取学号和密码成功 \t学号：" + xuehao);
			passwd.pop();// 拿到学号和密码之后 将学生信息弹出
			// 获取成绩页面
			// System.out.println("获取网页信息");
			// _log.info("获取网页信息");
			String GradePage = Tools.getGradePage(xuehao, upass);
			if(Tools.savepage(GradePage,xuehao)){
				System.out.println("保存网页成功");
			}
			// 拿到成绩页面，提取出学生信息和成绩
			_log.info("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);// 返回的是一个学生的所有成绩
			// 存入数据库
			_log.info("更新数据库");
			// 这里应当返回一个包含了新增成绩的栈 然后 发送出去
			// 额 又需要在数据库中 添加邮箱了。。。
			stu = JDBCTools.saveStudent(student);// 返回的是该学生的所有新增成绩
			newGrade.push(stu);// 已经考虑是否有重复了
		}
		// 判断是否有新成绩增加
		// System.out.println(newGrade);
//		if (!newGrade.peek().grade.isEmpty()) {
//			// 增加了新的成绩
//			_log.info("有新成绩增加");
//			EmailGrade.sendEmail(newGrade);
//			_log.info("邮件发送成功");
//		} else {
//			_log.info("没有增加新成绩");
//		}
		_log.info("运行结束");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 第一步从数据中获取学号和密码，返回值类型是Stack<IdAndPasswd>
		_log.info("服务启动");
		Stack<IdAndPasswd> passwd = JDBCTools.QueryPasswd(null);// 得到学号和密码
		Stack<Student> newGrade = new Stack<>();// 用来存放更新了成绩的学生
		while (!passwd.isEmpty()) {
			// System.out.println("获取学号和密码成功");

			Student stu = new Student();
			String xuehao = passwd.peek().getStuId();
			String upass = passwd.peek().getPassswd();
			_log.info("获取学号和密码成功 \t学号：" + xuehao);
			passwd.pop();// 拿到学号和密码之后 将学生信息弹出
			// 获取成绩页面
			// System.out.println("获取网页信息");
			// _log.info("获取网页信息");
			String GradePage = Tools.getGradePage(xuehao, upass);
			// 拿到成绩页面，提取出学生信息和成绩
			_log.info("已获取网页信息，正获取学生信息");
			// System.out.println("获取的网页为："+GradePage);
			Student student = Tools.getStuInfo(GradePage);// 返回的是一个学生的所有成绩
			// 存入数据库
			_log.info("存入数据库");
			// 这里应当返回一个包含了新增成绩的栈 然后 发送出去
			// 额 又需要在数据库中 添加邮箱了。。。
			stu = JDBCTools.saveStudent(student);// 返回的是该学生的所有新增成绩
			newGrade.push(stu);// 已经考虑是否有重复了
		}
		// 判断是否有新成绩增加
		// System.out.println(newGrade);
		if (!newGrade.peek().grade.isEmpty()) {
			// 增加了新的成绩
			_log.info("有新成绩增加");
			EmailGrade.sendEmail(newGrade);
			_log.info("发送成功");
		} else {
			_log.info("没有增加新成绩");
		}
		_log.info("运行结束");
	}

}