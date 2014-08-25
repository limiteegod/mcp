package com.mcp.fsd.check;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

public class FsdZuSanFuShiCheck extends FsdCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		boolean hit = true;
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int type = FsdConstants.getNubmerType(intNumber);
		if(type != FsdConstants.NUMBER_TYPE_ZUSAN)
		{
			hit  = false;
		}
		else
		{
			for(int i = 0; i < number.length; i++)
			{
				if(ticketNumber.indexOf(number[i]) < 0)
				{
					hit  = false;
				}
			}
		}
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
		}
		return cp;
	}

}
