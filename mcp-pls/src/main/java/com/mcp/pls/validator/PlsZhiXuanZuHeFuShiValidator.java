package com.mcp.pls.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.order.util.LotteryUtil;
import com.mcp.pls.common.PlsConstants;

/**
 * 排列三，直选组合复式，注数为所有的号码个数中选3个的排列数。
 * @author ming.li
 */
public class PlsZhiXuanZuHeFuShiValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{1}(,\\d{1}){0,9}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int[] detailNumberArray = LotteryUtil.getIntArrayFromStrArray(numbers.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryFuShiUtil.checkFuShiNumber(detailNumberArray, PlsConstants.MAX, PlsConstants.MIN);
		int count = MathUtil.getA(detailNumberArray.length, PlsConstants.DANSHI_LEN);
		return count;
	}
}
