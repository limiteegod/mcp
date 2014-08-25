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
 * 合买任务处理类，订单已经算奖的合买方案，进行返奖
 * @author ming.li
 */
public class HmPrizeTasklet implements Tasklet {
	
	public static Logger log = Logger.getLogger(HmPrizeTasklet.class);

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
            List<SchemeHm> schemeHmList = this.schemeHmService.findToAudit(pr);
            if(schemeHmList.size() == 0)
            {
                break;
            }
            for(SchemeHm scheme:schemeHmList)
            {
                long bonus = scheme.getBonus();
                if(bonus > 0)   //中奖金额大于0
                {
                    double bonusPerShare = bonus*1.0/scheme.getAmount();
                    try {
                        List<SchemeShare> shareList = this.schemeShareService.findAllBySchemeId(scheme.getId());
                        for(SchemeShare schemeShare:shareList)
                        {
                            long customerBonus = (long)(schemeShare.getAmount()*bonusPerShare);
                            //返奖
                            this.orderInter.prizeToCustomer(schemeShare.getCustomerId(), scheme.getStationId(), scheme.getId(), customerBonus, "scheme-hm");
                            this.schemeShareService.updateBonusById(customerBonus, schemeShare.getId());
                        }
                        //更新方案状态
                        this.schemeHmService.updateStatusById(SchemeState.AUDITED.getCode(), scheme.getId());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        log.error(e.getMessage());
                    }
                }
            }
        } while(true);
		return RepeatStatus.FINISHED;
    }

}
