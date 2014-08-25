package com.mcp.esf.check;

import com.mcp.esf.common.EsfConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class EsfQthzuFuShiCheck extends EsfCheck {
	
	private static Logger log = Logger.getLogger(EsfQthzuFuShiCheck.class);

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
		int hitCount = EsfConstants.getHitCount(numberArrayOne, numberArrayTwo);
		if(hitCount == 3)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_ELEVENTH, 1, prizeDescription);
		}
		return cp;
	}
}
