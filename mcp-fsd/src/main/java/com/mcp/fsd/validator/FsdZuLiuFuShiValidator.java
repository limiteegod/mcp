package com.mcp.fsd.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class FsdZuLiuFuShiValidator extends FsdValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{1}(,\\d{1}){3,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		String[] numberArray = numbers.split(",");
		int[] num = new int[numberArray.length];
		for(int j = 0; j < num.length; j++)
		{
			num[j] = Integer.parseInt(numberArray[j]);
		}
		super.checkSort(num);
		super.checkMargin(num, FsdConstants.MAX, FsdConstants.MIN);
		int count = MathUtil.getC(num.length, 3);
		return count;
	}

}
