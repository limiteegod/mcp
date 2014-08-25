/**
 * 
 */
package com.mcp.jc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.validator.JcValidator;

/**
 * @author ming.li
 *
 */
public abstract class JcAbstractValidator implements JcValidator {
	
	@Override
	public int validator(String number, int m, int n) throws CoreException {
		String split = "\\d{2}(,\\d{2}){0,}\\|\\d{12}\\|\\d{1,}(@\\d{1,}\\.\\d{1,}){0,1}(,\\d{1,}(@\\d{1,}\\.\\d{1,}){0,1}){0,}";
		String match = "(\\${0,1}" + split + "(&" + split +"){0,}){1}";
		String reg = "^" + match + "(;" + match + "){0,}$";
		if(!number.matches(reg))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return 0;
	}
}
