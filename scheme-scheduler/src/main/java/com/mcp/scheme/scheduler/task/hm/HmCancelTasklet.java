/**
 *
 */
package com.mcp.scheme.scheduler.task.hm;

import com.mcp.order.status.SchemeState;
import com.mcp.rmi.inter.OrderInter;
import com.mcp.scheme.model.SchemeHm;
import com.mcp.scheme.model.SchemeShare;
import com.mcp.scheme.service.SchemeHmService;
import com.mcp.scheme.service.SchemeShareService;
import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * 合买任务处理类，检测合买方案是否过期
 * @author ming.li
 */
public class HmCancelTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(HmCancelTasklet.class);

    @Autowired
    private SchemeHmService schemeHmService;

    @Autowired
    private SchemeShareService schemeShareService;

    @Autowired
    private OrderInter orderInter;

	/* (non-Javadoc)
     * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext)
     */
    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        int pIndex = 0, size = 10;
        do {
            PageRequest pr = new PageRequest(pIndex, size);
            List<SchemeHm> schemeHmList = this.schemeHmService.findToCancel(pr);
            if(schemeHmList.size() == 0)
            {
                break;
            }
            for(SchemeHm scheme:schemeHmList)
            {
                try {
                    List<SchemeShare> shareList = this.schemeShareService.findAllBySchemeId(scheme.getId());
                    for(SchemeShare schemeShare:shareList)
                    {
                        //退款
                        this.orderInter.refundToCustomer(schemeShare.getCustomerId(), scheme.getStationId(), scheme.getId(), schemeShare.getAmount(), "scheme-hm");
                    }
                    this.schemeHmService.updateStatusById(SchemeState.CANCELED.getCode(), scheme.getId());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }
        } while(true);
		return RepeatStatus.FINISHED;
    }

}
