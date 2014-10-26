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
 * Created by CH on 2014/10/26.
 */
@Component("feoRtKuaDuCheck")
public class FeoRtKuaDuCheck extends FeoCheck{
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String numbers = ticket.getNumbers();
        int[] swim = LotteryUtil.getIntArrayFromStrArray(numbers.split(LotteryUtil.POSITION_REG_SEP)[0].split(LotteryUtil.FUSHI_REG_SEP));
        String kuaDuNumber = numbers.split(LotteryUtil.POSITION_REG_SEP)[1];
        int hitKuaDu = 0;
        boolean hit = false;
        hitKuaDu = Math.abs(Integer.parseInt(number[swim[0]-1]) - Integer.parseInt(number[swim[1]-1]));
        String hitKuaDuStr = String.valueOf(hitKuaDu);
        if (kuaDuNumber.indexOf(hitKuaDuStr) > -1){
            hit = true;
        }
        if (hit){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, 1, prizeDescription);
        }
        return cp;
    }
}
