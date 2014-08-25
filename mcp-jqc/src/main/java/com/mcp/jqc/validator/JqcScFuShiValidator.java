/**
 * 
 */
package com.mcp.jqc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * @author ming.li
 *
 */
public class JqcScFuShiValidator extends JqcValidator {

	/* (non-Javadoc)
	 * @see com.mcp.order.validator.Validator#validator(java.lang.String)
	 */
	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|2|3]{1}(,[0|1|2|3]{1}){0,3}(\\|[0|1|2|3]{1}(,[0|1|2|3]{1}){0,3}){7}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		int count = 1;
		String[] numStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
		for(int i = 0; i < numStrArray.length; i++)
		{
			count = count*numStrArray[i].split(LotteryUtil.FUSHI_SEP).length;
		}
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

}
