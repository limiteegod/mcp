/**
 * 
 */
package com.mcp.feo.validator;

import com.mcp.feo.common.FeoConstants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;

/**
 * @author ming.li
 *
 */
public class FeoZsDanShiValidator extends FeoValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		for(int i = 0; i < items.length; i++)	
		{
			String item = items[i];
			if(!item.matches("^\\d{1}(,\\d{1}){3}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			int[] intArray = LotteryUtil.getIntArrayFromStrArray(item.split(LotteryUtil.FUSHI_REG_SEP));
			LotteryUtil.checkMargin(intArray, FeoConstants.MAX, FeoConstants.MIN);
			LotteryUtil.checkSortFromMinToMaxCanEqual(intArray);
			
			//必须是组6号
			int type = FeoConstants.getNumberType(intArray);
			if(type != FeoConstants.NUMBER_TYPE_ZS)
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + type + "-" + numbers);
			}
		}
		return items.length;
	}
	
}
