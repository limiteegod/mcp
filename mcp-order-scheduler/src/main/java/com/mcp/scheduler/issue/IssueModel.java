/**
 * 奖期流程核心类，所有的奖期流程由这个类进行管理。
 */
package com.mcp.scheduler.issue;

import com.mcp.core.util.CoreUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.ts.Term;
import com.mcp.order.mongo.service.MgCurTermService;
import com.mcp.order.mongo.util.NotifyUtil;
import com.mcp.order.service.GameService;
import com.mcp.order.service.TermLogService;
import com.mcp.order.service.TermService;
import com.mcp.order.service.TicketService;
import com.mcp.order.service.util.TermLogUtil;
import com.mcp.order.status.TermState;
import com.mcp.rmi.inter.SchemeInter;
import com.mcp.scheduler.common.SchConstants;
import com.mcp.scheduler.common.SchedulerContext;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;

/**
 * 奖期流程核心类，所有的奖期流程由这个类进行管理。
 * @author ming.li
 *
 */
public class IssueModel {
	
	private static final Logger log = Logger.getLogger(IssueModel.class);
	
	private Term term;
	
	private TicketService ticketService;
	
	private TermLogService termLogService;

    private MgCurTermService mgCurTermService;
	
	private TermService termService;
	
	private String gameCode;
	
	private String termCode;
	
	private String nextTermCode;
	
	private String iFolder;
	
	private int gameType;
	
	/**
	 * spring的上下文
	 */
	private ApplicationContext context;
	
	/**
	 * 更新期次状态，并记录期次操作日志，
	 * @param status
	 * @param updateDb
	 * @param suffix
	 */
	private void toStatus(TermState status, boolean updateDb, String suffix)
	{
		if(updateDb)
		{
			this.termService.updateStatusById(status.getCode(), term.getId());
		}
		this.term.setStatus(status.getCode());
	    String description = status.getDesc() + "," + suffix;
	    TermLogUtil.log(termLogService, term.getId(), description);
	    
	    log.info("gameCode:" + this.gameCode + ",termCode:" + this.termCode + "," + description);
	}
	
	/**
	 * 获得job的公有的参数
	 */
	private JobParametersBuilder getParamBuilder()
	{
		//准备参数
        JobParametersBuilder jobPara = new JobParametersBuilder();
        jobPara.addString("key", CoreUtil.getUUID());
        jobPara.addString("gameCode", this.gameCode);
        jobPara.addString("gameType", String.valueOf(this.gameType));
		jobPara.addString("termId", term.getId());
		jobPara.addString("termCode", this.termCode);
        jobPara.addString("nextTermCode", nextTermCode);
		jobPara.addString("iFolder", iFolder);
		return jobPara;
	}

	public IssueModel(Term term)
	{
		this.term = term;
		this.context = SchedulerContext.getInstance().getSpringContext();
		this.termLogService = context.getBean("termLogService", TermLogService.class);
		this.termService = context.getBean("termService", TermService.class);
		this.ticketService = context.getBean("ticketService", TicketService.class);
        this.mgCurTermService = context.getBean("mgCurTermService", MgCurTermService.class);
        //this.schemeInter = context.getBean("schemeInter", SchemeInter.class);
		GameService gameService = context.getBean("gameService", GameService.class);
		
		this.gameCode = term.getGameCode();
		this.termCode = term.getCode();
		this.nextTermCode = term.getNextCode();
		this.gameType = gameService.findOneByCode(gameCode).getType();
		iFolder = SchConstants.getIssueFolder(gameCode, termCode, gameType, term.getEndTime());
	}
	
	/**
	 * 开售
	 */
	public void open() throws Exception
	{
		toStatus(TermState.PRE_ON_SALE, false, "");
		
		//执行导出数据任务
		Job job = (Job)context.getBean("oJob");
	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
	    launcher.run(job, getParamBuilder().toJobParameters());
		
		toStatus(TermState.ON_SALE, true, "");
		NotifyUtil.sendN01(context, term);
	}
	
	/**
	 * 前台停售
	 */
	public void close() throws Exception
	{
		NotifyUtil.sendN01(context, term);
        this.mgCurTermService.updateTermCodeById(nextTermCode, term.getGameCode());
		toStatus(TermState.PREEND, false, "");
		
		//执行导出数据任务
		Job job = (Job)context.getBean("closeJob");
	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
	    launcher.run(job, getParamBuilder().toJobParameters());
	    
		Thread.sleep(2000);
		toStatus(TermState.END, true, "");
	}
	
	/**
	 * 后台停售
	 */
	public void sClose() throws Exception
	{
		toStatus(TermState.SEND, false, "");
		this.ticketService.issueClose(gameCode, termCode);
		
		//后台停售的业务逻辑
        Thread.sleep(2000);
		
        toStatus(TermState.SYNCHRONIZING, true, "");
        
		//TODO渠道之间同步
        Thread.sleep(2000);
        
        toStatus(TermState.SYNCHRONIZED, true, "");
	}
	
	/**
	 * 同步之后，导出数据文件
	 */
	public void exportData() throws Exception
	{
		toStatus(TermState.DRAW_EXPORT_DATA, false, "");
		
		//执行导出数据任务
		Job job = (Job)context.getBean("eJob");
	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
	    launcher.run(job, getParamBuilder().toJobParameters());
		
	    toStatus(TermState.WAITING_DRAW_NUMBER, true, "");
        //toStatus(TermState.DRAW, true, "");
	}
	
	
	
	/**
	 * 开奖之后
	 * 算奖
	 */
	public void draw() throws Exception
	{
		NotifyUtil.sendN01(context, term);	//发送开奖通知
		toStatus(TermState.IN_CALCULATE, false, "开奖号码为:" + term.getWinningNumber());
		
		//算奖
		Job job = (Job)context.getBean("cJob");
	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
	    launcher.run(job, getParamBuilder().toJobParameters());
        
        toStatus(TermState.CALCULATE, true, "");
        //不再告知方案引擎，由订单引擎自己处理方案
		//this.schemeInter.termChange(this.gameCode, this.termCode, this.nextTermCode, TermState.CALCULATE.getCode());
    	//竞彩和高频直接跳过审核
        if(gameType == ConstantValues.Game_Type_Jingcai.getCode() || gameType == ConstantValues.Game_Type_Gaopin.getCode())
		{
        	Thread.sleep(2000);
        	toStatus(TermState.CALCULATE_AUDITED, true, "");
		}
        /*else
        {
            Thread.sleep(2000);
            toStatus(TermState.CALCULATE_AUDITED, true, "");
        }*/
	}
	
	/**
	 * 返奖
	 * @throws Exception
	 */
	public void prize() throws Exception
	{
		toStatus(TermState.IN_PAYOUT, false, "");
		
		//返奖
		Job job = (Job)context.getBean("pJob");
	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
	    launcher.run(job, getParamBuilder().toJobParameters());
        
        toStatus(TermState.PAYOUT, true, "");
	}
	
	/**
	 * 结算
	 */
	public void audit() throws Exception
	{
		toStatus(TermState.IN_SETTLEMENT, false, "");
		
  		Job job = (Job)context.getBean("aJob");
  	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
  	    launcher.run(job, getParamBuilder().toJobParameters());
        
  	    Thread.sleep(2000);
        toStatus(TermState.SETTLEMENT, true, "");
	}
	
	/**
	 * 封存
	 */
	public void seal() throws Exception
	{
		toStatus(TermState.IN_SEAL, false, "");
		
		Job job = (Job)context.getBean("sealJob");
  	    JobLauncher launcher = (JobLauncher)context.getBean("syncJobLauncher");
  	    launcher.run(job, getParamBuilder().toJobParameters());
  	    
        toStatus(TermState.SEAL, true, "");
        NotifyUtil.sendN01(context, term, this.iFolder);
	}
}
