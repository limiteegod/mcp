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
public class FeoZsDanTuoValidator extends FeoValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		if(!numbers.matches("^\\d{1}\\$\\d{1}(,\\d{1}){1,6}$"))
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		String[] pArray = numbers.split(LotteryUtil.DANTUO_REG_SEP);
		String first = pArray[0];
		String second = pArray[1];
		
		int[] firstIntArray = LotteryUtil.getIntArrayFromStrArray(first.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryUtil.checkMargin(firstIntArray, FeoConstants.MAX, FeoConstants.MIN);
		LotteryUtil.checkSortFromMinToMax(firstIntArray);
		
		int[] secondIntArray = LotteryUtil.getIntArrayFromStrArray(second.split(LotteryUtil.FUSHI_REG_SEP));
		LotteryUtil.checkMargin(secondIntArray, FeoConstants.MAX, FeoConstants.MIN);
		LotteryUtil.checkSortFromMinToMax(secondIntArray);
		
		//号码不能重复
		int dCount = LotteryUtil.getHitCount(firstIntArray, secondIntArray);
		if(dCount > 0)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
				
		int count = MathUtil.getC(secondIntArray.length, 1);
		if(count < 2)
		{
			throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
		}
		return count;
	}
	
}
