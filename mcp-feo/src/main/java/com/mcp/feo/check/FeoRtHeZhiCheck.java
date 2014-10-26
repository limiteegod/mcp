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
        int[] swimArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.POSITION_REG_SEP)[0].split(LotteryUtil.FUSHI_REG_SEP));
        String chooseNumber = ticketNumber.split(LotteryUtil.POSITION_REG_SEP)[1];
        int hitSum = 0;
        boolean hitCount = false;
        for (int i = 0; i< swimArray.length; i++ ){
            hitSum += Integer.parseInt(number[swimArray[i]-1]);
        }
        String hitSumStr = String.format("%1$02d",hitSum);
        if (chooseNumber.indexOf(hitSumStr) > -1){
            hitCount = true;
        }
        if(hitCount)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, 1 , prizeDescription);
        }
        return cp;
    }
}
