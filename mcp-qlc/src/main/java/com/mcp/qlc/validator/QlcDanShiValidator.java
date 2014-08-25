package com.mcp.qlc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class QlcDanShiValidator extends QlcValidator {

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
			if(item.length() == 0)
			{
				throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
			}
			if(!item.matches("^\\d{2}(,\\d{2}){6}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			super.getCount(item);
		}
		return items.length;
	}

}
