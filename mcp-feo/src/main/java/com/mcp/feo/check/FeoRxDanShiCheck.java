package com.mcp.feo.check;

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
@Component("feoRxDanShiCheck")
public class FeoRxDanShiCheck extends FeoCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_SEP);
        int ren3Count = 0;
        int ren2Count = 0;
        int ren1Count = 0;
        int ren4Count = 0;
        for (int i = 0; i <ticketArray.length; i++){
            String danshi = ticketArray[i];
            String[] danshiArray = danshi.split(LotteryUtil.POSITION_REG_SEP);
            int count = 0;
            int blackCount = 0;
            for (int j = 0; j< danshiArray.length; j++){
                    if (danshiArray[j].equals("_")){
                        blackCount ++;
                    }else if (!danshiArray[j].equals(number[j].trim())){
                       break;
                    }
                count ++;
            }
            if (count == 4){ //中奖
                if (blackCount == 3){ //任选1
                    ren1Count ++;
                }else if (blackCount == 2) {//任选2
                    ren2Count ++;
                }else if (blackCount == 1){ //任选3
                    ren3Count ++;
                }else if (blackCount == 0){ //直选4
                    ren4Count ++;
                }
            }
        }
        if (ren3Count > 0){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, ren3Count, prizeDescription);
        }
        if (ren2Count > 0){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SIXTH, ren2Count, prizeDescription);
        }
        if (ren1Count > 0){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIFTH, ren1Count, prizeDescription);
        }
        if (ren4Count > 0){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_EIGHTH, ren4Count, prizeDescription);
        }
        return cp;
    }
}
