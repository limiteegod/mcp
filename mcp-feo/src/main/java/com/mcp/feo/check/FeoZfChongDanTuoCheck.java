package com.mcp.feo.check;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Created by CH on 2014/10/26.
 */
@Component("feoZfChongDanTuoCheck")
public class FeoZfChongDanTuoCheck extends FeoCheck {

    @Override
    public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
        CheckParam cp = new CheckParam();
        String numbers = ticket.getNumbers();
        int[] dNumberArray = LotteryUtil.getIntArrayFromStrArray(number);
        Map<Integer, Integer> dInfo = LotteryUtil.getInfo(dNumberArray);
        boolean hit = false;
        if (FeoConstants.getNumberType(dInfo) == FeoConstants.NUMBER_TYPE_ZF ){
            String[] numbersArray =  numbers.split(LotteryUtil.DANTUO_REG_SEP);
            int dan = Integer.parseInt(numbersArray[0]);
            int[] tuo = LotteryUtil.getIntArrayFromStrArray(numbersArray[1].split(LotteryUtil.FUSHI_REG_SEP));
            if (dInfo.get(dan) ==3 ){
                for (int i = 0; i < tuo.length; i++){
                  if (dInfo.containsKey(tuo[i])){
                      hit = true;
                      break;
                  }
                }
            }
        }
        if (hit){
            super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FOURTH, 1, prizeDescription);
        }
        return cp;
    }
}
