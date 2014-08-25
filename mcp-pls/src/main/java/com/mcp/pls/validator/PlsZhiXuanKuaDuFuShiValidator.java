package com.mcp.pls.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.order.util.LotteryUtil;
import com.mcp.pls.common.PlsConstants;

public class PlsZhiXuanKuaDuFuShiValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{1}(,\\d{1}){0,9}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int[] detailNumberArray = LotteryUtil.getIntArrayFromStrArray(numbers.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryFuShiUtil.checkFuShiNumber(detailNumberArray, PlsConstants.MAX, PlsConstants.MIN);
		int count = 0;
		for(int j = 0; j < detailNumberArray.length; j++)
		{
			count += PlsConstants.KUADU_COUNT[detailNumberArray[j]];
		}
		return count;
	}

}
