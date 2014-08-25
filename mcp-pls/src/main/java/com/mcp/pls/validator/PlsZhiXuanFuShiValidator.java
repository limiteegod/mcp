package com.mcp.pls.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.pls.common.PlsConstants;

public class PlsZhiXuanFuShiValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{1}(,\\d{1}){0,}(\\|\\d{1}(,\\d{1}){0,}){2}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = LotteryFuShiUtil.checkPositionFuShiNumberAndGetCount(numbers, PlsConstants.MAX, PlsConstants.MIN, 1);
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}
}
