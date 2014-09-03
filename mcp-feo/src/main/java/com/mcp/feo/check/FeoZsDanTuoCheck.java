/**
 * 
 */
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

import java.util.Map;
import java.util.Set;

/**
 * @author ming.li
 *
 */
@Component("feoZsDanTuoCheck")
public class FeoZsDanTuoCheck extends FeoCheck {


    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketNumberStrArray = ticketNumber.split(LotteryUtil.DANTUO_REG_SEP);
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        Map<Integer, Integer> dInfo = LotteryUtil.getInfo(dNumberArray);
        Set<Integer> keys = dInfo.keySet();
        if(FeoConstants.getNumberType(dInfo) == FeoConstants.NUMBER_TYPE_ZS)
        {
            boolean hit = true;
            int[] danIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberStrArray[0].split(LotteryUtil.FUSHI_REG_SEP));
            for(int dan:danIntArray)
            {
                if(dInfo.get(dan) == null)
                {
                    hit = false;
                    break;
                }
            }

            if(hit) {
                int tuoHitCount = 0;
                int[] tuoIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberStrArray[1].split(LotteryUtil.FUSHI_REG_SEP));
                for (int tuo : tuoIntArray)
                {
                    if(dInfo.get(tuo) != null)
                    {
                        tuoHitCount++;
                    }
                }
                if(tuoHitCount + danIntArray.length != 2)
                {
                    hit = false;
                }
            }

            if(hit)
            {
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
            }
        }
        return cp;
    }
}
