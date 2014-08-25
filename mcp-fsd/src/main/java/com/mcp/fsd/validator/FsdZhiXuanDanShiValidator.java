package com.mcp.fsd.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class FsdZhiXuanDanShiValidator extends FsdValidator {

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
			//正则表达式已经校验了边界
			if(!item.matches("^\\d{1}(\\|\\d{1}){2}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
			}
		}
		return items.length;
	}

}
