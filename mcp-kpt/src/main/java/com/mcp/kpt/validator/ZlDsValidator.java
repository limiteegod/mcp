package com.mcp.kpt.validator;

import com.mcp.kpt.common.KptConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;


public class ZlDsValidator extends KptValidator {

    public static Logger log = Logger.getLogger(ZlDsValidator.class);


    @Override
    public int validator(String numbers) throws CoreException {
        if(!numbers.matches("^\\d{2}(,\\d{2}){5}$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] numStrArray = numbers.split(LotteryUtil.FUSHI_REG_SEP);
        int[] numIntArray = LotteryUtil.getIntArrayFromStrArray(numStrArray);
        LotteryUtil.checkSortFromMinToMax(numIntArray);
        LotteryUtil.checkMargin(numIntArray, KptConstants.MAX, KptConstants.MIN);
        return 1;
    }

}
