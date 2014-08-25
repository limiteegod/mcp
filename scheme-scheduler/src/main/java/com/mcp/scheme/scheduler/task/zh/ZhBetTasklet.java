/**
 *
 */
package com.mcp.scheme.scheduler.task.zh;

import com.mcp.order.model.ts.TOrder;
import com.mcp.order.status.SchemeState;
import com.mcp.rmi.inter.OrderInter;
import com.mcp.scheme.model.SchemeZh;
import com.mcp.scheme.mongo.service.MgZhService;
import com.mcp.scheme.service.SchemeZhService;
import com.mcp.scheme.util.SchemeUtil;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author ming.li
 */
public class ZhBetTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(ZhBetTasklet.class);

	private String termCode;
	
	private String gameCode;
	
	private String nextTermCode;
	
	@Autowired
	private SchemeZhService schemeZhService;
	
	@Autowired
	private MgZhService mgZhService;
	
	@Autowired
	private OrderInter orderInter;

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public void setNextTermCode(String nextTermCode) {
		this.nextTermCode = nextTermCode;
	}

	/* (non-Javadoc)
     * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
    	int pIndex = 0, size = 100;
		do {
			PageRequest pr = new PageRequest(pIndex, size);
			List<SchemeZh> tList = this.mgZhService.findToDraw(gameCode, termCode, pr);
			for(SchemeZh t:tList)
			{
				SchemeZh rScheme = schemeZhService.incrNewOrder(t.getId(), 0, 0, nextTermCode);
				int rStatus = rScheme.getStatus();
				if(rStatus == SchemeState.FINISHED.getCode())
				{
					TOrder order = SchemeUtil.getOrderFromScheme(rScheme);
					orderInter.saveOrder(order);
					this.mgZhService.delete(gameCode, t.getId());
				}
				else if(rStatus == SchemeState.RUNNING.getCode())
				{
					TOrder order = SchemeUtil.getOrderFromScheme(rScheme);
					orderInter.saveOrder(order);
					this.mgZhService.incrNewOrder(gameCode, nextTermCode, t.getId());
				}
			}
			if(tList.size() == 0)
			{
				break;
			}
		} while(true);
		return RepeatStatus.FINISHED;
    }

}
