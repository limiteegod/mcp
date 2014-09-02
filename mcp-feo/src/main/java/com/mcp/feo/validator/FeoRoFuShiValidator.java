package com.mcp.feo.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by liming on 14-9-2.
 */
public class FeoRoFuShiValidator extends FeoValidator {

    @Override
    public int validator(String numbers) throws CoreException {
        String posReg = "(([1-8]{1}(,[1-8]){0,7})|_)";
        if(!numbers.matches("^" + posReg + "{1}(\\|" + posReg + "){3}$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] posStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
        int count = 0;
        for(int j = 0; j < posStrArray.length; j++)
        {
            if(!posStrArray[j].equals(LotteryUtil.BLANK_SEP))
            {
                int[] numIntArray = LotteryUtil.getIntArrayFromStrArray(posStrArray[j].split(LotteryUtil.FUSHI_REG_SEP));
                LotteryUtil.checkSortFromMinToMax(numIntArray);
                count += numIntArray.length;
            }
        }
        if(count <= 1)
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return count;
    }
}
