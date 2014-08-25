package com.mcp.pls.validator;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.util.LotteryUtil;


public class PlsZhiXuanDanShiValidator extends PlsValidator {

	@Override
	public int validator(String numbers) throws CoreException {
		String[] items = numbers.split(LotteryUtil.TICKET_REG_SEP);
		for(int i = 0; i < items.length; i++)
		{
			String item = items[i];
			//正则表达式已经校验了边界
			if(!item.matches("^\\d{1}(\\|\\d{1}){2}$"))
			{
				throw new CoreException(ErrCode.E2033, ErrCode.codeToMsg(ErrCode.E2033) + item);
			}
		}
		return items.length;
	}

}
