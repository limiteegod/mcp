package com.mcp.feo.check;

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
@Component("feoRtDanShiCheck")
public class FeoRtDanShiCheck extends FeoCheck {


    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
        for(int i = 0; i < ticketArray.length; i++)
        {
            int hitCount = FeoConstants.getRNumberHitCount(ticketArray[i], number);
            if(hitCount == 2)
            {
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, 1, prizeDescription);
            }
        }
        return cp;
    }
}
