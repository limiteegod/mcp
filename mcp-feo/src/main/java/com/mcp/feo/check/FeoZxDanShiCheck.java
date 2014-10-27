package com.mcp.feo.check;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

/**
 * Created by CH on 2014/10/27.
 */
@Component("feoZxDanShiCheck")
public class FeoZxDanShiCheck extends FeoCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] items = ticketNumber.split(LotteryUtil.TICKET_SEP);
        int hitCount = 0;
        int type = FeoConstants.getNumberType(LotteryUtil.getIntArrayFromStrArray(number));
        for (int i = 0; i < items.length ; i++){
            boolean hit = true;
            String[] danshi =items[i].split(LotteryUtil.FUSHI_SEP);
            for (int j=0; j< danshi.length; j ++){
                if (!danshi[i].equals(number[i])){
                    hit = false;
                    break;
                }
            }
           if (hit){
               hitCount ++;
           }
        }
        int level = FeoConstants.getPrizeLevel(type);
        if (hitCount > 0){
            super.getPrizeByLevel(cp, level, hitCount, prizeDescription);
        }
        return cp;
    }
}
