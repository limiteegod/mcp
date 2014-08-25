package com.mcp.order.validator;

import com.mcp.order.exception.CoreException;

public interface JcValidator {
	
	public int validator(String numbers, int m, int n) throws CoreException;
	
}
