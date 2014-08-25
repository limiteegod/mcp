/**
 * 
 */
package com.mcp.dlt.validator;

import com.mcp.dlt.common.DltConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryFuShiUtil;
import com.mcp.order.util.LotteryUtil;

/**
 * @author ming.li
 *
 */
public class DanShiValidator extends LotValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		for(int i = 0; i < items.length; i++)	
		{
			String item = items[i];
			if(item.length() == 0)
			{
				throw new CoreException(ErrCode.E2026, ErrCode.codeToMsg(ErrCode.E2026));
			}
			if(!item.matches("^\\d{2}(,\\d{2}){4}\\|\\d{2},\\d{2}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
			}
			String[] numberArray = item.split(LotteryUtil.POSITION_REG_SEP);
			LotteryFuShiUtil.checkFuShiNumber(numberArray[0], DltConstants.MAX_RED, DltConstants.MIN_RED);
			LotteryFuShiUtil.checkFuShiNumber(numberArray[1], DltConstants.MAX_BLUE, DltConstants.MIN_BLUE);
		}
		return items.length;
	}
	
}
