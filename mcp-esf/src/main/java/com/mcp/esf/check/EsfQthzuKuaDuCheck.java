package com.mcp.esf.check;

import com.mcp.esf.common.EsfConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

public class EsfQthzuKuaDuCheck extends EsfCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		boolean hit = false;
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int kuaDu = EsfConstants.getKuaDu(intNumber, 3);
		String[] arrayTwoStr = ticketNumber.split(",");
		int[] arrayTwo = new int[arrayTwoStr.length];
		for(int i = 0; i < arrayTwoStr.length; i++)
		{
			arrayTwo[i] = Integer.parseInt(arrayTwoStr[i]);
		}
		int[] arrayOne = new int[]{kuaDu};
		int hitCount = EsfConstants.getHitCount(arrayOne, arrayTwo);
		if(hitCount == 1)
		{
			hit = true;
		}
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_ELEVENTH, 1, prizeDescription);
		}
		return cp;
	}
}
