package com.mcp.esf.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;


public class EsfZyFuShiValidator extends EsfValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		if(items.length != 1)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		String item = items[0];
		if(!item.matches("^\\d{2}(,\\d{2}){0,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = getCount(item);
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
		return itemArray.length;
	}
}
