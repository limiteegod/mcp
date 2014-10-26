package com.mcp.feo.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.feo.check.FeoCheck;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/26.
 */
public class FeoZotChongDanTuoValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-8]\\$[1-8](,[1-8]){2,6}$";
        if (!numbers.matches(regex)){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        };
        String dan = numbers.split(LotteryUtil.DANTUO_REG_SEP)[0];
        String tuo = numbers.split(LotteryUtil.DANTUO_REG_SEP)[1];
        if (tuo.indexOf(dan) > -1){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        int[] tuoArray = LotteryUtil.getIntArrayFromStrArray(tuo.split(LotteryUtil.FUSHI_REG_SEP));
        LotteryUtil.checkSortFromMinToMax(tuoArray);
        return MathUtil.getC(tuoArray.length, 2);
    }
}
