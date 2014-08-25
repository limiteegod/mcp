package com.mcp.fsd.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class FsdZhiXuanFuShiCheck extends FsdCheck {
	
	private static Logger log = Logger.getLogger(FsdZhiXuanFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		log.info(Arrays.asList(number));
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split("\\|");
		boolean hit = true;
		for(int j = 0; j < ticketNumberArray.length; j++)
		{
			if(ticketNumberArray[j].indexOf(number[j]) < 0)
			{
				hit = false;
				break;
			}
		}
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
		}
		return cp;
	}

}
