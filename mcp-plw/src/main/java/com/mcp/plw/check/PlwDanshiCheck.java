package com.mcp.plw.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

public class PlwDanshiCheck extends PlwCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketArray = ticketNumber.split(";");
		for(int i = 0; i < ticketArray.length; i++)
		{
			String ticketStr = ticketArray[i];
			String[] ticketNumberArray = ticketStr.split("\\|");
			boolean hit = true;
			for(int j = 0; j < ticketNumberArray.length; j++)
			{
				if(!ticketNumberArray[j].equals(number[j]))
				{
					hit = false;
					break;
				}
			}
			if(hit)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
			}
		}
		return cp;
	}

}
