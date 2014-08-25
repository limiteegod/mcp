package com.mcp.scheme.scheduler.common;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.FileUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.rmi.inter.OrderInter;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * 定投结算模型
 * @author ming.li
 */
public class DtModel {
	
	public static Logger log = Logger.getLogger(DtModel.class);
	
	/**
	 * 渠道编码
	 */
	private String channelCode;
	
	public DtModel(String channelCode)
	{
		this.channelCode = channelCode;
	}
	
	
	public void bet() throws Exception
	{
		String srcFile = DtConstants.DT_FOLDER + "/" + channelCode + "/" + DtConstants.getDtFileName();
		String dtBetFileIn = DtConstants.DT_FOLDER + "/" + channelCode + "/S_" + DtConstants.getDtFileName();
		String dtBetFileOut = DtConstants.DT_FOLDER_OUT + "/" + channelCode + "/S_DTRESULT_" + DtConstants.getDtFileName();
		String dtBetFileOutCount = DtConstants.DT_FOLDER_OUT + "/" + channelCode + "/COUNT_S_DTRESULT_" + DtConstants.getDtFileName();
		String tFile = DtConstants.DT_FOLDER_OUT + "/" + channelCode + "/DTRESULT_" + DtConstants.getDtFileName();
		
		List<String> infoList = FileUtil.split(srcFile, dtBetFileIn, false);
		String md5 = MD5Util.getFileMD5String(new File(dtBetFileIn));
		if(md5.equals(infoList.get(0)))
		{
			ApplicationContext context = SchemeContext.getInstance().getSpringContext();
			String jobName = "dtJob";
			JobParametersBuilder jobPara = new JobParametersBuilder();  //设置文件路径参数
			jobPara.addString("key", UUID.randomUUID().toString().replace("-", ""));
			jobPara.addString("channelCode", channelCode);
			jobPara.addString("dtBetFileIn", dtBetFileIn);
			jobPara.addString("dtBetFileOut", dtBetFileOut);
			jobPara.addString("dtBetFileOutCount", dtBetFileOutCount);

			Job job = (Job)context.getBean(jobName);
		    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
		    launcher.run(job, jobPara.toJobParameters());
		    
		    FileUtil.gather(dtBetFileOutCount, dtBetFileOut, tFile);
		    log.info("dt方案数据处理完成");
		}
		else
		{
			log.error("md5校验失败,对方:" + infoList.get(0) + ",我方:" + md5);
		}
	}
	
	public void fj() throws Exception
	{
		OrderInter orderInter = SchemeContext.getInstance().getBean("orderInter", OrderInter.class);
		String games = DtConstants.DT_GAMES;
		String[] gameArray = games.split(",");
		for(int i = 0; i < gameArray.length; i++)
		{
			String gameCode = gameArray[i];
			List<String> fList = orderInter.getLastDayPrizedFileList(gameCode, this.channelCode);
			for(String s:fList)
			{
				log.info(s);
			}
		}
	}
	
	/**
	 * 
	 * @param gameCode
	 * @param termCode
	 * @param nextTermCode
	 */
	public void termDrawed(String gameCode, String termCode, String nextTermCode) throws Exception
	{
		String folder = DtConstants.getDtBetFolder(gameCode, termCode);
		File f = new File(folder);
		if(!f.exists())
		{
			f.mkdirs();
		}
		String jobName = "dtTermDrawJob";
		JobParametersBuilder jobPara = new JobParametersBuilder();  //设置文件路径参数
		jobPara.addString("key", CoreUtil.getUUID());
		jobPara.addString("folder", folder);
		jobPara.addString("gameCode", gameCode);
		jobPara.addString("termCode", termCode);
		jobPara.addString("nextTermCode", nextTermCode);
		jobPara.addString("dtBetFileIn", folder + "/dtBetFileIn.txt");
		jobPara.addString("dtBetFileOut", folder + "/dtBetFileOut.txt");
		ApplicationContext context = SchemeContext.getInstance().getSpringContext();
		Job job = (Job)context.getBean(jobName);
	    JobLauncher launcher = (JobLauncher)context.getBean("jobLauncher");
	    launcher.run(job, jobPara.toJobParameters());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		DtModel dcm = new DtModel(DtConstants.DT_CHANNELCODE);
		dcm.fj();
	}
}
