package com.mcp.esf.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfZfDanTuoCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfZfDanTuoCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		
		int fCount;
		String[] redArray = ticketNumber.split("\\$");
		
		String[] fCountArray = redArray[0].split(",");
		fCount = fCountArray.length;
		String[] sCountArray = redArray[1].split(",");
		
		//如果胆码全中
		if(getHitCount(fCountArray, number) == fCount)
		{
			int tuoHit = getHitCount(sCountArray, number);
			int count = MathUtil.getC(tuoHit, 4 - fCount);
			if(count > 0)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FOURTH, count, prizeDescription);
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
