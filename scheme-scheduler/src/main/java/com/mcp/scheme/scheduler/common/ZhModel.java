package com.mcp.scheme.scheduler.common;

import com.mcp.core.util.CoreUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;

import java.io.File;

public class ZhModel {
	
	/**
	 * 
	 * @param gameCode
	 * @param termCode
	 * @param nextTermCode
	 */
	public void termDrawed(String gameCode, String termCode, String nextTermCode) throws Exception
	{
		String folder = ZhConstants.getZhFolder(gameCode, termCode);
		File f = new File(folder);
		if(!f.exists())
		{
			f.mkdirs();
		}
		String jobName = "zhTermDrawJob";
		JobParametersBuilder jobPara = new JobParametersBuilder();  //设置文件路径参数
		jobPara.addString("key", CoreUtil.getUUID());
		jobPara.addString("gameCode", gameCode);
		jobPara.addString("termCode", termCode);
		jobPara.addString("nextTermCode", nextTermCode);
		ApplicationContext context = SchemeContext.getInstance().getSpringContext();
		Job job = (Job)context.getBean(jobName);
	    JobLauncher launcher = (JobLauncher)context.getBean("jobLauncher");
	    launcher.run(job, jobPara.toJobParameters());
	}
}
