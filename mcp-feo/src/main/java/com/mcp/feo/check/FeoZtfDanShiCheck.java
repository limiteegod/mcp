/**
 * 
 */
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
 * @author ming.li
 *
 */
@Component("feoZtfDanShiCheck")
public class FeoZtfDanShiCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        for(int i = 0; i < ticketArray.length; i++)
        {
            int[] ticketNumberIntArray = LotteryUtil.getIntArrayFromStrArray(ticketArray[i].split(LotteryUtil.FUSHI_REG_SEP));
            if(FeoConstants.getNumberType(dNumberArray) == FeoConstants.NUMBER_TYPE_ZTF)
            {
                int hitCount = LotteryUtil.getHitCount(dNumberArray, ticketNumberIntArray);
                if(hitCount == 4)
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
                }
            }
        }
        return cp;
    }

}
