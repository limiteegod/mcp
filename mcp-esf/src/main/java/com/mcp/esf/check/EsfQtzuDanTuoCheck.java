package com.mcp.esf.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class EsfQtzuDanTuoCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfQtzuDanTuoCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] numberArrayTwo = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1])};

		int fCount, sCount;
		String[] redArray = ticketNumber.split("\\$");
		String[] fCountArray = redArray[0].split(",");
		fCount = fCountArray.length;
		int[] fIntCountArray = new int[fCount];
		for(int i = 0; i < fCount; i++)
		{
			fIntCountArray[i] = Integer.parseInt(fCountArray[i]);
		}
		String[] sCountArray = redArray[1].split(",");
		sCount = sCountArray.length;
		int[] sIntCountArray = new int[sCount];
		for(int i = 0; i < sCount; i++)
		{
			sIntCountArray[i] = Integer.parseInt(sCountArray[i]);
		}
		int danHit = getHitCount(fIntCountArray, numberArrayTwo);
		int tuoHit = getHitCount(sIntCountArray, numberArrayTwo);
		if(danHit == fCount && tuoHit == 2 - danHit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_NINTH, 1, prizeDescription);
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
