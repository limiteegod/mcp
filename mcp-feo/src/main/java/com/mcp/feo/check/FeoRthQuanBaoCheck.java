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

import java.util.Map;
import java.util.Set;

/**
 * Created by CH on 2014/10/26.
 */
@Component("feoRthQuanBaoCheck")
public class FeoRthQuanBaoCheck extends FeoCheck {
    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String ticketNumber  = ticket.getNumbers();
        int[] ticketArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.FUSHI_REG_SEP));
        Map<Integer,Integer> dInfo = LotteryUtil.getInfo(LotteryUtil.getIntArrayFromStrArray(number));
        Map<Integer,Integer> chooseInfo = LotteryUtil.getInfo(ticketArray);

        int type = FeoConstants.getNumberType(dInfo);
        int hitCount = 0;
        int hitNumber = 0;
        if (type == FeoConstants.NUMBER_TYPE_ZTF ){
            for (int i =0; i< ticketArray.length; i++){
                if (!dInfo.containsKey(ticketArray[i])){
                     break;
                }
                hitNumber ++;
            }
            if (hitNumber == 3){
                hitCount = 1;
            }
        }else if (type == FeoConstants.NUMBER_TYPE_ZOT){
            boolean flag = false;
            for (int i = 0; i< ticketArray.length; i++){
                if (!dInfo.containsKey(ticketArray[i])){
                    break;
                }
                if (dInfo.get(ticketArray[i]) == 2 && chooseInfo.get(ticketArray[i]) == 2){
                     flag = true;
                }
                hitNumber ++;
            }
            if (hitNumber ==3){
                if (flag){
                    hitCount = 1;
                }else {
                    hitCount = 2;
                }
            }
        }else if (type == FeoConstants.NUMBER_TYPE_ZF ){
            if (chooseInfo.size() <= dInfo.size()){
                Set<Integer> keys = chooseInfo.keySet();
                for (Integer key : keys){
                    if (dInfo.containsKey(key)){
                        if (chooseInfo.get(key) > dInfo.get(key) ){
                            hitCount = 0;
                            break;
                        }else if (chooseInfo.get(key) > 2){
                            hitCount = 1;
                            break;
                        }else{
                            hitCount = 3;
                            break;
                        }
                    }else{
                        hitCount = 0;
                        break;
                    }
                }
            }
        }else if (type == FeoConstants.NUMBER_TYPE_ZS){
            if (chooseInfo.size() == dInfo.size()){
                  hitCount = 2;
            }
        }else{
            if (dInfo.size() == 1 && chooseInfo.size() == 1){
                if (dInfo.containsKey(ticketArray[0])){
                    hitCount = 4;
                }
            }
        }

        if(hitCount >= 1)
        {
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, hitCount, prizeDescription);
        }
        return cp;
    }
}
