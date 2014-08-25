package com.mcp.kt.validator;

import com.mcp.kt.common.KtConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;


public class HezhiPuTongValidator extends KtValidator {

    public static Logger log = Logger.getLogger(HezhiPuTongValidator.class);

    @Override
    public int validator(String numbers) throws CoreException {
        if(!numbers.matches("^\\d{1,2}(,\\d{1,2}){0,13}$"))
        {
            throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
        }
        String[] numArray = numbers.split(LotteryUtil.FUSHI_REG_SEP);
        int[] numIntArray = LotteryUtil.getIntArrayFromStrArray(numArray);
        LotteryUtil.checkSortFromMinToMax(numIntArray);
        LotteryUtil.checkMargin(numIntArray, KtConstants.HEZHI_MAX, KtConstants.HEZHI_MIN);
        return numArray.length;
    }
}
