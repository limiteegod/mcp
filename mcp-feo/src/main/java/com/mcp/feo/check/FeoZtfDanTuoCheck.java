/**
 * 
 */
package com.mcp.feo.check;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

/**
 * @author ming.li
 *
 */
@Component("feoZtfDanTuoCheck")
public class FeoZtfDanTuoCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketNumberStrArray = ticketNumber.split(LotteryUtil.DANTUO_REG_SEP);
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        if(FeoConstants.getNumberType(dNumberArray) == FeoConstants.NUMBER_TYPE_ZTF)
        {
            int[] danIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberStrArray[0].split(LotteryUtil.FUSHI_REG_SEP));
            int danHitCount = LotteryUtil.getHitCount(danIntArray, dNumberArray);
            if(danHitCount == danIntArray.length)
            {
                int[] tuoIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberStrArray[1].split(LotteryUtil.FUSHI_REG_SEP));
                int tuoHitCount = LotteryUtil.getHitCount(tuoIntArray, dNumberArray);
                if(tuoHitCount == 4 - danHitCount)
                {
                    super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
                }
            }
        }
        return cp;
    }
}
