package com.mcp.feo.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Created by CH on 2014/10/26.
 */
@Component("feoZotChongDanTuoCheck")
public class FeoZotChongDanTuoCheck extends FeoCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketNumberStrArray = ticketNumber.split(LotteryUtil.DANTUO_REG_SEP);
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        int[] tuoArray =  LotteryUtil.getIntArrayFromStrArray(ticketNumberStrArray[1].split(LotteryUtil.FUSHI_REG_SEP));
        Map<Integer, Integer> dInfo = LotteryUtil.getInfo(dNumberArray);
        Set<Integer> keys = dInfo.keySet();
        if (keys.size() == 3 ){
            boolean hit = false;
            int dan = Integer.parseInt(ticketNumberStrArray[0]);
            if (dInfo.containsKey(dan) && dInfo.get(dan) ==2 ){
                int hitCount = 0;
                for (int i = 0; i< tuoArray.length; i++){
                    if (dInfo.containsKey(tuoArray[i])){
                        hitCount ++ ;
                    }
                }
                if (hitCount >= 2 ){
                    hit = true;
                }
            }
            if(hit)
            {
                super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1 , prizeDescription);
            }
        }
        return cp;
    }
}
