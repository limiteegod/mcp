package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

/**
 * 排列三直选单式，算法思想，按位比对，如果有一位不匹配，则未中奖，三位都匹配，则中奖。
 * @author ming.li
 */
public class PlsZhiXuanDanShiCheck extends PlsCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] pIntNumber = LotteryUtil.getIntArrayFromStrArray(number);
		String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
		for(int i = 0; i < ticketArray.length; i++)
		{
			String ticketStr = ticketArray[i];
			boolean hit = super.isZhiXuanFuShiHit(ticketStr, pIntNumber);
			if(hit)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
			}
		}
		return cp;
	}

}
