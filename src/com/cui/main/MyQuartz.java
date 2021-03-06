package com.cui.main;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;


import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MyQuartz {
	public void run() throws Exception {
	    Logger log = LoggerFactory.getLogger(MyQuartz.class);

	    log.info("------- Initializing -------------------");

	    // First we must get a reference to a scheduler
	    SchedulerFactory sf = new StdSchedulerFactory();
	    Scheduler sched = sf.getScheduler();

	    log.info("------- Initialization Complete --------");

	    log.info("------- Scheduling Jobs ----------------");

	    // jobs can be scheduled before sched.start() has been called

	    JobDetail job = newJob(NewGetGrade.class).withIdentity("job1", "group1").build();
	    //在这里更改运行的时间 （second minute hour day	week month year）
	    CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0 0 0,4,20 * * ?"))
	        .build();
	    //将工作加入工作序列中 到时间 自动启动
	    Date ft = sched.scheduleJob(job, trigger);
	    log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
	             + trigger.getCronExpression());
	    
	    log.info("------- Starting Scheduler ----------------");

	    // All of the jobs have been added to the scheduler, but none of the
	    // jobs
	    // will run until the scheduler has been started
	    sched.start();
	    log.info("------- Started Scheduler -----------------");


	    //log.com.cui.Bean("------- Shutting Down ---------------------");

	    //sched.shutdown(true);

	    //log.com.cui.Bean("------- Shutdown Complete -----------------");

	    SchedulerMetaData metaData = sched.getMetaData();
	    log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

	  }
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MyQuartz example = new MyQuartz();
		example.run();
	}

}
