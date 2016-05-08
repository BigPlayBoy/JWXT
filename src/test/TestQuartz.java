package test;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Getgrade;

/**
 * 管理代码的自动运行， 1.需要要运行的函数 2.运行的时间 3.最好便于修改
 * 
 * @author MYKING
 *
 */
public class TestQuartz {
	// 创建日志功能
	Logger log = LoggerFactory.getLogger(TestQuartz.class);
	String runTime = "";// 运行的时间格式"0 0 12 * * ?" Fire at 12pm (noon) every day
	String	jobClass="";//需要运行的任务的class
	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public TestQuartz(String runTime) {
		super();
		this.runTime = runTime;
	}

	public void start() throws SchedulerException {
		log.info("------- Initializing -------------------");

		// First we must get a reference to a scheduler
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();

		log.info("------- Initialization Complete --------");
		log.info("------- Scheduling Jobs ----------------");

		// jobs can be scheduled before sched.start() has been called
		//创建job  需要知道任务的类 
		JobDetail job = newJob(TestJob.class).withIdentity("job1", "group1").build();
		// 在这里更改运行的时间 （second minute hour day week month year）
		CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1")
				.withSchedule(cronSchedule("0/10 * 0-23 * * ?")).build();
		// 将工作加入工作序列中 到时间 自动启动
		Date ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
				+ trigger.getCronExpression());

		log.info("------- Starting Scheduler ----------------");
		sched.start();//启动任务
		// log.info("------- Shutting Down ---------------------");

		// sched.shutdown(true);

		// log.info("------- Shutdown Complete -----------------");
	}

	public static void main(String[] args) {
		TestQuartz quartz = new TestQuartz("0 0 12 * * ？");
		try {
			quartz.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
