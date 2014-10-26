package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * Created by liming on 14-9-2.
 */
public class FeoRtQuanBaoValidator extends FeoValidator {

    @Override
    public int validator(String numbers) throws CoreException {
        //任选2 全包
        String posReg = "[1-8](,[1-8])";
        if(!numbers.matches("^" + posReg + "$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] posStrArray = numbers.split(LotteryUtil.FUSHI_REG_SEP);
        if (posStrArray.length != 2){
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        int [] intPosArray = LotteryUtil.getIntArrayFromStrArray(posStrArray);
        LotteryUtil.checkSortFromMinToMaxCanEqual(intPosArray);
        LotteryUtil.checkMargin(intPosArray, FeoConstants.MAX, FeoConstants.MIN);
        int count = FeoConstants.getRTwoNumberQuanBaoCount(intPosArray);
        if(count < 6)
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        return count;
    }

}
