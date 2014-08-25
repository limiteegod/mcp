/**
 * 
 */
package com.mcp.jqc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;

/**
 * @author ming.li
 *
 */
public class JqcScDanShiValidator extends JqcValidator {

	/* (non-Javadoc)
	 * @see com.mcp.order.validator.Validator#validator(java.lang.String)
	 */
	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|2|3]{1}(\\|[0|1|2|3]{1}){7}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		return 1;
	}

}
