package com.mcp.fsd.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

public class FsdZuSanDanShiValidator extends FsdValidator {

	@Override
	public int validator(String numbers) throws CoreException {
        String[] items = numbers.split(";");
        for(int i = 0; i < items.length; i++)
        {
            String item = items[i];
            if(!item.matches("^\\d{1}(,\\d{1}){2}$"))
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
            int[] num = LotteryUtil.getIntArrayFromStrArray(item.split(","));
            LotteryUtil.checkSortFromMinToMaxCanEqual(num);
            super.checkMargin(num, FsdConstants.MAX, FsdConstants.MIN);
            if(FsdConstants.getNubmerType(num) != FsdConstants.NUMBER_TYPE_ZUSAN)
            {
                throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
            }
        }
        return items.length;
	}

}
