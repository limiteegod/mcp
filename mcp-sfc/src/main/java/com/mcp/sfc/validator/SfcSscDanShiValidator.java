package com.mcp.sfc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

public class SfcSscDanShiValidator extends SfcValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|3]{1}(\\|[0|1|3]{1}){13}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		return 1;
	}

}
