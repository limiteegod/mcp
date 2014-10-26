package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/26.
 */
public class FeoRtKuaDuValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-4](,[1-4])\\|[0-7](,[0-7])*$";
        if (!numbers.matches(regex)){
            throw  new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        //检查数值
        String subNumber = numbers.split(LotteryUtil.POSITION_REG_SEP)[1];
        int [] numberArray = LotteryUtil.getIntArrayFromStrArray(subNumber.split(LotteryUtil.FUSHI_REG_SEP));
        LotteryUtil.checkSortFromMinToMax(numberArray);
        LotteryUtil.checkMargin(numberArray, 7, 0);
        return FeoConstants.getRtKuaDuCount(numberArray);
    }
}
