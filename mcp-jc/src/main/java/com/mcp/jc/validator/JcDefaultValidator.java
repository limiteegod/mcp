package com.mcp.jc.validator;

import com.mcp.jc.common.JcUtils;
import com.mcp.order.exception.CoreException;

public class JcDefaultValidator extends JcAbstractValidator {

	@Override
	public int validator(String number, int m, int n) throws CoreException {
		super.validator(number, m, n);
		int count = JcUtils.getOrderCount(number, m, n);
		return count;
	}

}
