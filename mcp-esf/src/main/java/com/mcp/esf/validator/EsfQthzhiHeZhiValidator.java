package com.mcp.esf.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.esf.common.EsfConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class EsfQthzhiHeZhiValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{2}(,\\d{2}){0,}$"))
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
		super.checkMargin(intArray, EsfConstants.QTHZU_HEZHI_MAX, EsfConstants.QTHZU_HEZHI_MIN);
		int count = 0;
		for(int i = 0; i < intArray.length; i++)
		{
			count += 6*EsfConstants.QTHZU_HEZHI_COUNT[intArray[i]];
		}
		return count;
	}
}
