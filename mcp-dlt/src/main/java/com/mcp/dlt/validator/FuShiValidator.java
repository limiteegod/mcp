package com.mcp.dlt.validator;

import com.mcp.dlt.common.DltConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.order.util.LotteryUtil;

public class FuShiValidator extends LotValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{2}(,\\d{2}){4,}\\|\\d{2}(,\\d{2}){1,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		String[] numberArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
		int redCount = LotteryFuShiUtil.checkFuShiNumberAndGetCount(numberArray[0], DltConstants.MAX_RED, DltConstants.MIN_RED, DltConstants.DANSHI_RED_LEN);
		int blueCount = LotteryFuShiUtil.checkFuShiNumberAndGetCount(numberArray[1], DltConstants.MAX_BLUE, DltConstants.MIN_BLUE, DltConstants.DANSHI_BLUE_LEN);
		int count = redCount*blueCount;
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

}
