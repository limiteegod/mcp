package com.mcp.sfc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

public class SfcSscFuShiValidator extends SfcValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|3]{1}(,[0|1|3]{1}){0,2}(\\|[0|1|3]{1}(,[0|1|3]{1}){0,2}){13}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		int count = 1;
		String[] numStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
		for(int i = 0; i < numStrArray.length; i++)
		{
			count = count*numStrArray[i].split(LotteryUtil.FUSHI_SEP).length;
		}
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}
}
