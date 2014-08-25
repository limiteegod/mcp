package com.mcp.scheduler.thread;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.dao.specification.TermSpecification;
import com.mcp.order.model.ts.Term;
import com.mcp.order.service.TermService;
import com.mcp.order.status.TermState;
import com.mcp.scheduler.common.SchedulerContext;
import com.mcp.scheduler.issue.IssueModel;
import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;

import java.util.Iterator;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

public class JcDrawThread implements Runnable {
	
	public static Logger log = Logger.getLogger(JcDrawThread.class);
	
	/**
	 * 游戏代码
	 */
	private String gameCode;
	
	public JcDrawThread(String gameCode)
	{
		this.gameCode = gameCode;
	}

	@Override
	public void run() {
		
		while(true)
		{
			log.info("扫描竞彩后台停售及开奖奖期......................");
			//算奖
			TermService termService = SchedulerContext.getInstance().getBean("termService", TermService.class);
        	Page<Term> termsToDraw;
        	try {
        		Specifications<Term> specs = where(TermSpecification.isStatusEqual(TermState.DRAW.getCode()));
                specs = specs.and(TermSpecification.isGameCodeEqual(gameCode));
                PageRequest pr = new PageRequest(0, 1);
        		termsToDraw = termService.findAllByStatusAndUpdate(specs, TermState.IN_CALCULATE.getCode(), pr);
	        }
        	catch(StaleObjectStateException e)
	    	{
	    		continue;
	    	}
        	Iterator<Term> termIt = termsToDraw.iterator();
        	while(termIt.hasNext())
        	{
        		Term t = termIt.next();
    	        try {
					new IssueModel(t).draw();
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
        	}
        	
        	//后台停售
        	List<Term> gtEndList;
        	try {
        		PageRequest pr = new PageRequest(0, 2);
        		gtEndList = termService.findToSEnd(ConstantValues.Game_Type_Jingcai.getCode(), gameCode, TermState.SEND.getCode(), pr);
        	} catch(StaleObjectStateException e)
        	{
        		continue;
        	}
        	for(int i = 0; i < gtEndList.size(); i++)
            {
            	Term t = gtEndList.get(i);
        	    try {
					new IssueModel(t).sClose();
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e.getMessage());
				}
            }
        	
        	//10秒的扫描间隔
        	try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
	}

}
