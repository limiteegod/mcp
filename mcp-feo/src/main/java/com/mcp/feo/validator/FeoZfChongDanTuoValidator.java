package com.mcp.feo.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;
import org.springframework.stereotype.Component;

/**
 * Created by CH on 2014/10/26.
 */
@Component("feoZfChongDanTuoValidator")
public class FeoZfChongDanTuoValidator extends FeoValidator {

    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-8]\\$[1-8](,[1-8]){0,7}$";
        if (!numbers.matches(regex)){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] numbersArray =  numbers.split(LotteryUtil.DANTUO_REG_SEP);
        String dan =numbersArray[0];
        String tuo = numbersArray[1];
        if (tuo.indexOf(dan) > -1){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] tuoArray = tuo.split(LotteryUtil.FUSHI_REG_SEP);
        LotteryUtil.checkSortFromMinToMax(LotteryUtil.getIntArrayFromStrArray(tuoArray));
        return MathUtil.getC(tuoArray.length, 1);
    }
}
