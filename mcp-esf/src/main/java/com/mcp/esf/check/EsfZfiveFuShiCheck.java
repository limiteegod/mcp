package com.mcp.esf.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfZfiveFuShiCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfZfiveFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int hitCount = this.getHitCount(ticketNumber, number);
		int count = MathUtil.getC(hitCount, 5);
		if(count > 0)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIFTH, count, prizeDescription);
		}
		return cp;
	}

	public int getHitCount(String ticketNumber, String[] number)
	{
		int hitCount = 0;
		String[] ticketNumberArray = ticketNumber.split(",");
		for(int i = 0; i < ticketNumberArray.length; i++)
		{
			for(int j = 0; j < number.length; j++)
			{
				if(ticketNumberArray[i].equals(number[j]))
				{
					hitCount++;
					break;
				}
			}
		}
		return hitCount;
	}
}
