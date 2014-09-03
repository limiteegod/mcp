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
@Component("feoZotFuShiCheck")
public class FeoZotFuShiCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        Map<Integer, Integer> dInfo = LotteryUtil.getInfo(dNumberArray);
        Set<Integer> keys = dInfo.keySet();
        if(keys.size() == 3)
        {
            int[] ticketNumberIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.FUSHI_REG_SEP));
            Map<Integer, Integer> tInfo = LotteryUtil.getInfo(ticketNumberIntArray);
            boolean hit = true;
            for(int key:keys)
            {
                if(tInfo.get(key) == null)
                {
                    hit = false;
                }
            }
            if(hit)
            {
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
            }
        }
        return cp;
    }

}
