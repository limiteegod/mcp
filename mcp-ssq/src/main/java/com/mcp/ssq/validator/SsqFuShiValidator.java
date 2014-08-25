package com.mcp.ssq.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class SsqFuShiValidator extends SsqValidator {

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
		if(!item.matches("^\\d{2}(,\\d{2}){5,}\\|\\d{2}(,\\d{2}){0,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		SsqCountDetail cd = super.getCount(item);
		if(cd.getCount() == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return cd.getCount();
	}

}
