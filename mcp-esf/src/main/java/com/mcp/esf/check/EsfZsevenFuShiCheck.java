package com.mcp.esf.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfZsevenFuShiCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfZsevenFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split(",");
		int hitCount = this.getHitCount(ticketNumberArray, number);
		if(hitCount == 5)
		{
			int count = MathUtil.getC(ticketNumberArray.length - 5, 2);
			if(count > 0)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SEVENTH, count, prizeDescription);
			}
		}
		return cp;
	}

	public int getHitCount(String[] ticketNumberArray, String[] number)
	{
		int hitCount = 0;
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
