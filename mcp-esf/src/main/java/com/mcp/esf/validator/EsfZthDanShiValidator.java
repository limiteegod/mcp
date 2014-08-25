package com.mcp.esf.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class EsfZthDanShiValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		String[] items = numbers.split(";");
		for(int i = 0; i < items.length; i++)	
		{
			String item = items[i];
			if(!item.matches("^\\d{2}(,\\d{2}){2}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
			}
			getCount(item);
		}
		return items.length;
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
		return itemArray.length;
	}
}
