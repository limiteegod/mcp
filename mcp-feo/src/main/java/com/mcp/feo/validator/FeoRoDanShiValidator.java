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
public class FeoRoDanShiValidator extends FeoValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(";");
		for(int i = 0; i < items.length; i++)	
		{
			String item = items[i];
			if(!item.matches("^[\\d{1}|_](\\|[\\d{1}|_]){3}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033));
			}
			
			String[] strArray = item.split(LotteryUtil.POSITION_REG_SEP);
			FeoConstants.checkRNumber(strArray);
			
			int[] lenArray = new int[4];
			for(int j = 0; j < strArray.length; j++)
			{
				if(strArray[j].equals("_"))
				{
					lenArray[j] = 0;
				}
				else
				{
					lenArray[j] = strArray[j].split(LotteryUtil.FUSHI_REG_SEP).length;
				}
			}
		}
		return items.length;
	}
	
}
