package com.mcp.esf.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfQthzhiFuShiCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfQthzhiFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split(",");
		int[] numberArrayOne = new int[ticketNumberArray.length];
		for(int i = 0; i < numberArrayOne.length; i++)
		{
			numberArrayOne[i] = Integer.parseInt(ticketNumberArray[i]);
		}
		int[] numberArrayTwo = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int hitCount = this.getHitCount(numberArrayOne, numberArrayTwo);
		if(hitCount == 3)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_TWELFTH, 1, prizeDescription);
		}
		return cp;
	}

	public int getHitCount(int[] numberArrayOne, int[] numberArrayTwo)
	{
		int hitCount = 0;
		for(int i = 0; i < numberArrayOne.length; i++)
		{
			for(int j = 0; j < numberArrayTwo.length; j++)
			{
				if(numberArrayOne[i] == numberArrayTwo[j])
				{
					hitCount++;
					break;
				}
			}
		}
		return hitCount;
	}
}
