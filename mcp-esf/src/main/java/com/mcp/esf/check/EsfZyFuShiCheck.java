package com.mcp.esf.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import org.apache.log4j.Logger;


public class EsfZyFuShiCheck extends EsfCheck {

    public static Logger log = Logger.getLogger(EsfZyFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] ticketNumberIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.FUSHI_REG_SEP));
		int hitCount = LotteryUtil.getHitCount(ticketNumberIntArray, new int[]{Integer.parseInt(number[0])});
		if(hitCount == 1)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
		}
		return cp;
	}

}
