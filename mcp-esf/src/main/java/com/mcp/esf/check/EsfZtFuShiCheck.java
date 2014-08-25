package com.mcp.esf.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

public class EsfZtFuShiCheck extends EsfCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		long prize = 0;
		int[] ticketNumberIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.FUSHI_REG_SEP));
		int[] drawNumberIntArray = LotteryUtil.getIntArrayFromStrArray(number);
		int hitCount = LotteryUtil.getHitCount(ticketNumberIntArray, drawNumberIntArray);
		int count = MathUtil.getC(hitCount, 2);
		if(count > 0)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, count, prizeDescription);
		}
		return cp;
	}
}
