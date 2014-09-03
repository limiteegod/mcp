package com.mcp.feo.check;

import com.mcp.core.util.MathUtil;
import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

/**
 * Created by liming on 14-9-2.
 */
@Component("feoRthFuShiCheck")
public class FeoRthFuShiCheck extends FeoCheck {


    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int hitCount = FeoConstants.getRNumberHitCount(ticketNumber, number);
        if(hitCount >= 3)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, MathUtil.getC(hitCount, 3), prizeDescription);
        }
        return cp;
    }
}
