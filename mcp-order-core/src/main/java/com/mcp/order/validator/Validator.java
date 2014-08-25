package com.mcp.order.validator;

import com.mcp.order.exception.CoreException;

public interface Validator {
	
	public int validator(String numbers) throws CoreException;
	
}
