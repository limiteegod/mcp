package com.mcp.pls.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.pls.common.PlsConstants;

public class PlsZuXuanDanShiValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{1},\\d{1},\\d{1}(;\\d{1},\\d{1},\\d{1}){0,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		String[] numberArray = numbers.split(";");
		for(int i = 0; i < numberArray.length; i++)
		{
			String[] numStrArray = numberArray[i].split(",");
			int[] num = new int[numStrArray.length];
			for(int j = 0; j < num.length; j++)
			{
				num[j] = Integer.parseInt(numStrArray[j]);
			}
			if(PlsConstants.getNubmerType(num) == PlsConstants.NUMBER_TYPE_BAOZI)
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			this.checkSort(num);
			super.checkMargin(num, PlsConstants.MAX, PlsConstants.MIN);
		}
		return numberArray.length;
	}

	/**
	 * 号码必须从小到大排列，可以相等
	 * @param numbers
	 * @throws CoreException
	 */
	protected void checkSort(int[] numbers)
	{
		if(numbers.length > 1)
		{
			for(int i = 1; i < numbers.length; i++)
			{
				if(numbers[i] < numbers[i - 1])
				{
					throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
				}
			}
		}
	}
}
