package com.mcp.scheduler.quartz;

import com.mcp.scheduler.common.SchConstants;
import com.mcp.scheduler.common.SchedulerContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class SchemeJob {
	
	private String gameCode;
	
	public SchemeJob(String gameCode)
	{
		this.gameCode = gameCode;
	}
	
	public void run() throws Exception
	{
		String folder = this.getSchemeFolder();
		File f = new File(folder);
		if(!f.exists())
		{
			f.mkdirs();
		}
		String jobName = "schemeJob";
        String schemeFileName = "schemes_for_handle.txt";
        String schemeFileIn = folder + "/" + schemeFileName;
        String schemeFileOut = folder + "/" + "schemes_for_handle_out.txt";
		JobParametersBuilder jobPara = new JobParametersBuilder();  //设置文件路径参数
		jobPara.addString("key", UUID.randomUUID().toString().replace("-", ""));
		jobPara.addString("gameCode", gameCode);
		jobPara.addString("schemeFileName", schemeFileName);
		jobPara.addString("schemeFileIn", schemeFileIn);
		jobPara.addString("schemeFileOut", schemeFileOut);
		ApplicationContext context = SchedulerContext.getInstance().getSpringContext();
		Job job = (Job)context.getBean(jobName);
	    JobLauncher launcher = (JobLauncher)context.getBean("jobLauncher");
	    launcher.run(job, jobPara.toJobParameters());
	}
	
	private String getSchemeFolder()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		String dateString = dateFormat.format(new Date());
		String year = dateString.substring(0, 4);
		String month = dateString.substring(5, 7);
		String day = dateString.substring(8, 10);
		String hour = dateString.substring(11, 13);
		String minute = dateString.substring(14, 16);
		StringBuffer sb = new StringBuffer();
		sb.append(SchConstants.ISSUE_FOLDER);
		sb.append("/");
		sb.append("scheme/");
		sb.append(gameCode);
		sb.append("/");
		sb.append(year);
		sb.append("/");
		sb.append(month);
		sb.append("/");
		sb.append(day);
		sb.append("/");
		sb.append(hour);
		sb.append("/");
		sb.append(minute);
		return sb.toString();
	}
}
