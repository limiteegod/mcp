package com.mcp.esf.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class EsfQtzuFuShiValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{2}(,\\d{2}){2,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = getCount(numbers);
		return count;
	}

	private int getCount(String item)
	{
		String[] itemArray = item.split(",");
		int[] intArray = new int[itemArray.length];
		for(int i = 0; i < intArray.length; i++)
		{
			intArray[i] = Integer.parseInt(itemArray[i]);
		}
		super.checkSort(intArray);
		super.checkMargin(intArray);
		return MathUtil.getC(itemArray.length, 2);
	}
}
