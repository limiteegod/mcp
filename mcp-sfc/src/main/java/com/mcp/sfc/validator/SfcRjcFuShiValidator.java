/**
 * 
 */
package com.mcp.sfc.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;
import com.mcp.sfc.common.SfcConstants;

/**
 * @author ming.li
 *
 */
public class SfcRjcFuShiValidator extends SfcValidator {

	/* (non-Javadoc)
	 * @see com.mcp.order.validator.Validator#validator(java.lang.String)
	 */
	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^[0|1|3|_]{1}(,[0|1|3|_]{1}){0,2}(\\|[0|1|3|_]{1}(,[0|1|3|_]{1}){0,2}){13}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + numbers);
		}
		int count = 1;
		int bCount = 0;
		String[] numStrArray = numbers.split(LotteryUtil.POSITION_REG_SEP);
		for(int i = 0; i < numStrArray.length; i++)
		{
			String[] posArray = numStrArray[i].split(LotteryUtil.FUSHI_SEP);
			if(numStrArray[i].indexOf("_") > -1)
			{
				if(posArray.length > 1)
				{
					throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
				}
				bCount++;
			}
			count = count*posArray.length;
		}
		//必须选9场
		if(SfcConstants.RJ_BLANK_COUNT != bCount)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		if(count == 1)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}

}
