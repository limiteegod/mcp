package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;


public class PlsZhiXuanFuShiCheck extends PlsCheck {
	
	private static Logger log = Logger.getLogger(PlsZhiXuanFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		int[] pIntNumber = LotteryUtil.getIntArrayFromStrArray(number);
		String ticketNumber  = ticket.getNumbers();
		boolean hit = super.isZhiXuanFuShiHit(ticketNumber, pIntNumber);
		if(hit)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
		}
		return cp;
	}

}
