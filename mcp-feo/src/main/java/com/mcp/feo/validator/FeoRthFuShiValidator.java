package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by liming on 14-9-2.
 */
public class FeoRthFuShiValidator extends FeoValidator {

    @Override
    public int validator(String numbers) throws CoreException {
        String posReg = "(([1-8]{1}(,[1-8]){0,7})|_)";
        if(!numbers.matches("^" + posReg + "{1}(\\|" + posReg + "){3}$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] posStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
        FeoConstants.checkRNumber(posStrArray);
        int count = FeoConstants.getRNumberCount(posStrArray, 3);
        if(count < 2)
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return count;
    }

}
