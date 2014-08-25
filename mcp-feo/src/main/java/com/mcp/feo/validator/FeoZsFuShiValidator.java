/**
 * 
 */
package com.mcp.feo.validator;

import com.mcp.core.util.MathUtil;
import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * @author ming.li
 *
 */
public class FeoZsFuShiValidator extends FeoValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{1}(,\\d{1}){2,7}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		int[] intArray = LotteryUtil.getIntArrayFromStrArray(numbers.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryUtil.checkMargin(intArray, FeoConstants.MAX, FeoConstants.MIN);
		LotteryUtil.checkSortFromMinToMax(intArray);
		
		int count = MathUtil.getC(intArray.length, 2);
		return count;
	}
	
}
