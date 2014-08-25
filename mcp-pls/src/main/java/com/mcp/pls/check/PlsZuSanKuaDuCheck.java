package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.pls.common.PlsConstants;

public class PlsZuSanKuaDuCheck extends PlsCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		boolean hit = false;
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int kuaDu = PlsConstants.getKuaDu(intNumber);
		int type = PlsConstants.getNubmerType(intNumber);
		if(type == PlsConstants.NUMBER_TYPE_ZUSAN && ticketNumber.indexOf(String.valueOf(kuaDu)) > 0)
		{
			hit = true;
		}
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
		}
		return cp;
	}

}
