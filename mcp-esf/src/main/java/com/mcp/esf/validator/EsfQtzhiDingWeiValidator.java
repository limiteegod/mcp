package com.mcp.esf.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.esf.common.EsfConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

public class EsfQtzhiDingWeiValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{2}(,\\d{2}){0,}\\|\\d{2}(,\\d{2}){0,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = getCount(numbers);
		if(count < 2)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

	private int getCount(String item)
	{
		String[] redArray = item.split(LotteryUtil.POSITION_REG_SEP);
		String[] fCountArray = redArray[0].split(LotteryUtil.FUSHI_REG_SEP);
		int[] fIntArray = LotteryUtil.getIntArrayFromStrArray(fCountArray);
		LotteryUtil.checkSortFromMinToMax(fIntArray);
		LotteryUtil.checkMargin(fIntArray, EsfConstants.MAX, EsfConstants.MIN);
		String[] sCountArray = redArray[1].split(LotteryUtil.FUSHI_REG_SEP);
		int[] sIntArray = LotteryUtil.getIntArrayFromStrArray(sCountArray);
		LotteryUtil.checkSortFromMinToMax(sIntArray);
		LotteryUtil.checkMargin(sIntArray, EsfConstants.MAX, EsfConstants.MIN);
		return LotteryUtil.getCount(new int[][]{fIntArray, sIntArray}, EsfConstants.MAX, false);
	}
}
