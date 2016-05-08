package test;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * 需要继承job接口  然后重写execute方法
 * @author MYKING
 *
 */
public class TestJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("这里是执行的任务");
	}

}
