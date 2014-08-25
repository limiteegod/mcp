package com.mcp.scheduler.quartz;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.specification.TermSpecification;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.ts.Game;
import com.mcp.order.model.ts.Term;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.TermService;
import com.mcp.order.status.TermState;
import com.mcp.scheduler.common.SchConstants;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheduler.issue.ClearModel;
import com.mcp.scheduler.issue.IssueModel;
import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

public class QuartzJob {
	
	private static Logger log = Logger.getLogger(QuartzJob.class);
	
	public void close() throws Exception
	{
		/**
         * 停售到时间点的奖期
         */
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
		List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	long offset = g.getOffset() * 1000L;
        	List<Term> gtEndList;
        	try {
        		PageRequest pr = new PageRequest(0, 2);
        		gtEndList = termService.findToEnd(gameCode, offset, TermState.PREEND.getCode(), pr);
        	} catch(StaleObjectStateException e)
        	{
        		continue;
        	}
            for(int i = 0; i < gtEndList.size(); i++)
            {
            	Term gt = gtEndList.get(i);
        	    new IssueModel(gt).close();
            }
        }        
	}
	
	/**
	 * 后台停售
	 * @throws Exception
	 */
	public void sClose() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	//竟彩后台停售，采用单线程模型，和算奖也不能同时进行
        	if(g.getType() == ConstantValues.Game_Type_Jingcai.getCode())
        	{
        		continue;
        	}
        	String gameCode = g.getCode();
        	List<Term> gtEndList;
        	try {
        		PageRequest pr = new PageRequest(0, 2);
        		gtEndList = termService.findToSEnd(g.getType(), gameCode, TermState.SEND.getCode(), pr);
        	} catch(StaleObjectStateException e)
        	{
        		continue;
        	}
            for(int i = 0; i < gtEndList.size(); i++)
            {
            	Term gt = gtEndList.get(i);
        	    new IssueModel(gt).sClose();
            }
        }
	}
	
	/**
     * 开始销售到时间点的奖期
     */
	public void open() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	List<Term> gtList;
        	try {
                PageRequest pr = new PageRequest(0, 2);
        		gtList = termService.findToOpen(gameCode, TermState.PRE_ON_SALE.getCode(), pr);
        	} catch(StaleObjectStateException e)
        	{
        		continue;
        	}
            for(int i = 0; i < gtList.size(); i++)
            {
            	Term gt = gtList.get(i);
                new IssueModel(gt).open();
            }
        }
	}
	
	/**
     * 已经同步的奖期，导出数据
     */
	public void exportData() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	Page<Term> termsToExportData;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.SYNCHRONIZED.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 2);
                
        		termsToExportData = termService.findAllByStatusAndUpdate(specs, TermState.DRAW_EXPORT_DATA.getCode(), pr);
	        } catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToExportData.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    		    new IssueModel(t).exportData();
        	}
        }
	}
	
	/**
	 * 对于已经开奖的奖期，算奖
	 */
	public void draw() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
		
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	//竟彩算奖，采用单线程模型，
        	if(g.getType() == ConstantValues.Game_Type_Jingcai.getCode())
        	{
        		continue;
        	}
        	Page<Term> termsToDraw;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.DRAW.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 2);
        		termsToDraw = termService.findAllByStatusAndUpdate(specs, TermState.IN_CALCULATE.getCode(), pr);
	        } catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToDraw.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    	        new IssueModel(t).draw();
        	}
        }
	}
	
	/**
	 * 对于已经通过算奖审核的奖期，返奖
	 */
	public void prize() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	
        	Page<Term> termsToPrize;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.CALCULATE_AUDITED.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 2);
        		termsToPrize = termService.findAllByStatusAndUpdate(specs, TermState.IN_PAYOUT.getCode(), pr);
	        } catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToPrize.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    	        new IssueModel(t).prize();
        	}
        }
	}
	
	/**
	 * 对于已经返奖的奖期，结算
	 */
	public void audit() throws Exception
	{
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	Page<Term> termsToAudit;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.PAYOUT.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 2);
        		termsToAudit = termService.findAllByStatusAndUpdate(specs, TermState.IN_SETTLEMENT.getCode(), pr);
	        } catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToAudit.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    	        new IssueModel(t).audit();
        	}
        }
	}
	
	/**
	 * 对于已经结算的奖期，封存
	 */
	public void seal() throws Exception
    {
		TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	Game g = gameList.get(j);
        	String gameCode = g.getCode();
        	Page<Term> termsToSeal;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.SETTLEMENT.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 2);
                
        		termsToSeal = termService.findAllByStatusAndUpdate(specs, TermState.IN_SEAL.getCode(), pr);
	        } catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToSeal.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    	        new IssueModel(t).seal();
        	}
        }
    }
	
	/**
	 * 处理方案
	 * @throws Exception
	 */
	public void scheme() throws Exception
	{
		List<Game> gameList = LotteryContext.getInstance().getAllGames();
        for (int j = 0; j < gameList.size(); j++) {
        	new SchemeJob(gameList.get(j).getCode()).run();
        }
	}
	
	//TODO 帐户过期的扫描
	
	/**
	 * 日结，每天的凌晨30分触发
	 * @throws Exception
	 */
	public void dailyClear() throws Exception
	{
        log.info("开始日结任务........" + new Date());
        new ClearModel().clearOldIssue();
	}

}
