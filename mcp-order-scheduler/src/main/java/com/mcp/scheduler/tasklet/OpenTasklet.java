package com.mcp.scheduler.tasklet;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.mongo.service.MgCurTermService;
import com.mcp.order.service.OrderService;
import com.mcp.order.service.util.PrintUtil;
import com.mcp.scheduler.common.SchedulerContext;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.annotation.PostConstruct;
import java.util.Iterator;

public class OpenTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(OpenTasklet.class);
	
	private String termCode;
	
	private int gameType;
	
	private String gameCode;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MgCurTermService mgCurTermService;
	

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameType(String gameType) {
		this.gameType = Integer.parseInt(gameType);
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@PostConstruct
	public void init()
	{
		
	}

	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
		//普通游戏和高频，更新当前期信息
		if(gameType == ConstantValues.Game_Type_Normal.getCode() || gameType == ConstantValues.Game_Type_Gaopin.getCode())
		{
			mgCurTermService.updateTermCodeById(termCode, gameCode);
		}
        //预售票改为等待打印
		int pIndex = 0, size = 100;
		do {
			PageRequest pr = new PageRequest(pIndex, size);
			Page<TOrder> prPage = orderService.queryPresale(gameCode, termCode, pr);
			Iterator<TOrder> it = prPage.iterator();
			while(it.hasNext())
			{
				TOrder t = it.next();
                try {
                    orderService.preparePrintForPresale(t);
                    PrintUtil.newOrder(SchedulerContext.getInstance().getSpringContext(), t);
                } catch (Exception e)
                {
                    log.error("处理预售订单出现错误,id:" + t.getId());
                }
			}
			if(!prPage.hasNextPage())
			{
				break;
			}
		} while(true);
		return RepeatStatus.FINISHED;
	}
}
