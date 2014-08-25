package com.mcp.qxc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class QxcFuShiValidator extends QxcValidator {

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
		if(!item.matches("^\\d{1}(,\\d{1}){0,}(\\|\\d{1}(,\\d{1}){0,}){6}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = super.getCount(item);
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

}
