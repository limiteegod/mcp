package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.pls.common.PlsConstants;

public class PlsZhiXuanZuHeDanTuoCheck extends PlsCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split("\\$");
		String ticketDanNumber = ticketNumberArray[0];
		String numberStr = number[0] + "|" + number[1] + "|" + number[2];
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		boolean danHit = true, tuoHit = true;
		if(PlsConstants.getNubmerType(intNumber) == PlsConstants.NUMBER_TYPE_ZULIU)
		{
			String[] ticketDanNumberArray = ticketDanNumber.split(",");
			for(int i = 0; i < ticketDanNumberArray.length; i++)
			{
				if(numberStr.indexOf(ticketDanNumberArray[i]) < 0)
				{
					danHit = false;
					break;
				}
			}
			for(int i = 0; i < number.length; i++)
			{
				if(ticketNumber.indexOf(number[i]) < 0)
				{
					tuoHit = false;
					break;
				}
			}
		}
		else
		{
			danHit = false;
			tuoHit = false;
		}
		if(danHit && tuoHit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
		}
		return cp;
	}

}
