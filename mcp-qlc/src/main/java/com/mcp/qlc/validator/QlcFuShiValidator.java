package com.mcp.qlc.validator;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class QlcFuShiValidator extends QlcValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(StringUtil.isEmpty(numbers))
		{
			throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
		}
		if(!numbers.matches("^\\d{2}(,\\d{2}){7,}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int count = super.getCount(numbers);
		return count;
	}

}
