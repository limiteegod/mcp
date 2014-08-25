package com.mcp.fsd.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class FsdZhiXuanFuShiValidator extends FsdValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		if(items.length != 1)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		String item = items[0];
		if(item.length() == 0)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!item.matches("^\\d{1}(,\\d{1}){0,}(\\|\\d{1}(,\\d{1}){0,}){2}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = getCount(item);
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

	protected int getCount(String number)
	{
		String[] nArray = number.split("\\|");
		int count = 1;
		for(int i = 0; i < nArray.length; i++)
		{
			String[] detailNumberStrArray = nArray[i].split(",");
			int[] detailNumberArray = new int[detailNumberStrArray.length];
			for(int j = 0; j < detailNumberStrArray.length; j++)
			{
				detailNumberArray[j] = Integer.parseInt(detailNumberStrArray[j]);
			}
			checkSort(detailNumberArray);
			checkMargin(detailNumberArray);
			count *= detailNumberArray.length;
		}
		return count;
	}
}
