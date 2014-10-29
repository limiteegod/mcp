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
 * Created by CH on 2014/10/28.
 */
@Component("feoRxZuHeCheck")
public class FeoRxZuHeCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber = ticket.getNumbers();
        String[] numberStrArray = ticketNumber.split(LotteryUtil.POSITION_REG_SEP);
        int hitCount = FeoConstants.getRNumberHitCount(ticketNumber, number);
        if(hitCount >= 1)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIFTH, MathUtil.getC(hitCount, 1), prizeDescription);
        }
        if(hitCount >= 2)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, MathUtil.getC(hitCount, 2), prizeDescription);
        }
        if(hitCount >= 3)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, MathUtil.getC(hitCount, 3), prizeDescription);
        }
        if(hitCount >= 4)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_EIGHTH, MathUtil.getC(hitCount, 4), prizeDescription);
        }
        return cp;
    }
}
