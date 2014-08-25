package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

public class PlsZhiXuanKuaDuFuShiCheck extends PlsCheck {
	
	//private static final Logger log = Logger.getLogger(PlsZhiXuanKuaDuFuShiCheck.class);

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] intNumber = LotteryUtil.getIntArrayFromStrArray(number);
		int kuaDu = LotteryUtil.getKuaDu(intNumber);
		int[] ticketIntArray = LotteryUtil.getIntArrayFromStrArray(ticketNumber.split(LotteryUtil.FUSHI_REG_SEP));
		int hitCount = LotteryUtil.getHitCount(ticketIntArray, new int[]{kuaDu});
		if(hitCount > 0)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, 1, prizeDescription);
		}
		return cp;
	}

}
