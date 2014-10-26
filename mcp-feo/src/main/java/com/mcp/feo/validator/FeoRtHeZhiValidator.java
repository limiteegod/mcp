package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/24.
 */
public class FeoRtHeZhiValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-4](,[1-4])\\|[0-1][1-8](,[0-1][1-8])*$";
        if (!numbers.matches(regex)){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String swim = numbers.split(LotteryUtil.POSITION_REG_SEP)[0];
        String sumNumber = numbers.split(LotteryUtil.POSITION_REG_SEP)[1];
        int[] sumArray = LotteryUtil.getIntArrayFromStrArray(sumNumber.split(LotteryUtil.FUSHI_REG_SEP));
        LotteryUtil.checkSortFromMinToMax(sumArray);
        LotteryUtil.checkMargin(sumArray, 16, 2);
        int count = FeoConstants.getRtHeZhiCount(sumArray);
        if (count < 1 ){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return count;
    }
}
