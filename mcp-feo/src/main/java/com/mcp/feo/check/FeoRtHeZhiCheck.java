package com.mcp.feo.check;

import com.mcp.core.util.MathUtil;
import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

/**
 * Created by CH on 2014/10/24.
 */
@Component("feoRtHeZhiCheck")
public class FeoRtHeZhiCheck extends FeoCheck{
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int hitCount = FeoConstants.getRtHeZhiHitCount(ticketNumber, number);
        if(hitCount >= 1)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, hitCount, prizeDescription);
        }
        return cp;
    }
}
