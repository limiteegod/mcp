package com.mcp.sfc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;
import com.mcp.sfc.common.SfcConstants;

public class SfcRjcDanShiValidator extends SfcValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|3|_]{1}(\\|[0|1|3|_]{1}){13}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		int bCount = 0;
		String[] numStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
		for(int i = 0; i < numStrArray.length; i++)
		{
			if(numStrArray[i].indexOf("_") > -1)
			{
				bCount++;
			}
		}
		//必须选9场
		if(SfcConstants.RJ_BLANK_COUNT != bCount)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return 1;
	}
}
