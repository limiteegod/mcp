package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by CH on 2014/10/28.
 */
public class FeoRxZuHeValidator extends FeoValidator {
    @Override
    public int validator(String numbers) throws CoreException {
        String regex = "^[1-8](,[1-8]){0,7}(\\|[1-8](,[1-8]){0,7}){3}$";
        if (!numbers.matches(regex)){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] posStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
        FeoConstants.checkRNumber(posStrArray);
        //组合 首先获得任一的注数
        int count = 0 ;
        count += FeoConstants.getRNumberCount(posStrArray, 1);
        count += FeoConstants.getRNumberCount(posStrArray, 2);
        count += FeoConstants.getRNumberCount(posStrArray, 3);
        count += FeoConstants.getRNumberCount(posStrArray, 4);
        return count;
    }
}
