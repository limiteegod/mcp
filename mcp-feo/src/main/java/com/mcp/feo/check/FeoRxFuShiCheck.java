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
@Component("feoRxFuShiCheck")
public class FeoRxFuShiCheck extends FeoCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] numberStrArray = ticketNumber.split(LotteryUtil.POSITION_REG_SEP);
        int hitCount = 0;
        int blackCount = 0;
        for(int i = 0; i < numberStrArray.length; i++)
        {
            if (numberStrArray[i].equals(LotteryUtil.BLANK_SEP)){
                   blackCount ++ ;
            }
            if(numberStrArray[i].indexOf(number[i]) > -1)
            {
                hitCount++;
            }
        }
        if (blackCount + hitCount == 4){ //中奖
            if (blackCount == 0){
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_EIGHTH, MathUtil.getC(hitCount, 4), prizeDescription);
            }else if(blackCount == 1){
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, MathUtil.getC(hitCount, 3), prizeDescription);
            }else if(blackCount == 2){
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, MathUtil.getC(hitCount, 2), prizeDescription);
            }else if(blackCount == 3){
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIFTH, MathUtil.getC(hitCount, 1), prizeDescription);
            }
        }
        return cp;
    }
}
