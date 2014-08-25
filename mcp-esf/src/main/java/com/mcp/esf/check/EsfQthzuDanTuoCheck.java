package com.mcp.esf.check;

import com.mcp.esf.common.EsfConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfQthzuDanTuoCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfQthzuDanTuoCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] numberArrayTwo = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};

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
		int danHit = EsfConstants.getHitCount(fIntCountArray, numberArrayTwo);
		int tuoHit = EsfConstants.getHitCount(sIntCountArray, numberArrayTwo);
		if(danHit == fCount && tuoHit == 3 - danHit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_ELEVENTH, 1, prizeDescription);
		}
		return cp;
	}
}
