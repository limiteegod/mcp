package com.mcp.plw.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class PlwDanShiValidator extends PlwValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		if(items.length == 0)
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		for(int i = 0; i < items.length; i++)	
		{
			String item = items[i];
			if(!item.matches("^\\d{1}(\\|\\d{1}){4}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
			}
			super.getCount(item);
		}
		return items.length;
	}

}
