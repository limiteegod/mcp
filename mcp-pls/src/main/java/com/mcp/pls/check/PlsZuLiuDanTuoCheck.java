package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.pls.common.PlsConstants;

public class PlsZuLiuDanTuoCheck extends PlsCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split("\\$");
		String numberStr = number[0] + "|" + number[1] + "|" + number[2];
		boolean hit = false;
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int type = PlsConstants.getNubmerType(intNumber);
		if(type == PlsConstants.NUMBER_TYPE_ZULIU)
		{
			boolean danHit = true;
			String[] danArray = ticketNumberArray[0].split(",");
			for(int i = 0; i < danArray.length; i++)
			{
				if(numberStr.indexOf(danArray[i]) < 0)
				{
					danHit = false;
				}
			}
			if(danHit && ticketNumber.indexOf(number[0]) > -1 &&
					ticketNumber.indexOf(number[1]) > -1 && ticketNumber.indexOf(number[2]) > -1)
			{
				hit = true;
			}
		}
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
		}
		return cp;
	}

}
