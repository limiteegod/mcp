package com.mcp.esf.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;


public class EsfQthzhiDanShiCheck extends EsfCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
		int[] drawNumberIntArray = LotteryUtil.getIntArrayFromStrArray(number, 0, 3);
		for(int i = 0; i < ticketArray.length; i++)
		{
			int[] ticketNumberIntArray = LotteryUtil.getIntArrayFromStrArray(ticketArray[i].split(LotteryUtil.POSITION_REG_SEP));
			int hitCount = LotteryUtil.getHitCountByOrder(ticketNumberIntArray, drawNumberIntArray);
			if(hitCount == 3)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_TWELFTH, 1, prizeDescription);
			}
		}
		return cp;
	}
}
