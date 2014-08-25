package com.mcp.esf.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

/**
 * 中奖条件，胆码必须全中，tuoHit的个数中选2-danHit即为注数
 * @author ming.li
 */
public class EsfZtDanTuoCheck extends EsfCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int fCount;
		String[] redArray = ticketNumber.split(LotteryUtil.DANTUO_REG_SEP);
		int[] fCountIntArray = LotteryUtil.getIntArrayFromStrArray(redArray[0].split(LotteryUtil.FUSHI_REG_SEP));
		fCount = fCountIntArray.length;
		int[] sCountIntArray = LotteryUtil.getIntArrayFromStrArray(redArray[1].split(LotteryUtil.FUSHI_REG_SEP));
		int[] drawNumberIntArray = LotteryUtil.getIntArrayFromStrArray(number);
		//如果胆码全中
		if(LotteryUtil.getHitCount(fCountIntArray, drawNumberIntArray) == fCount)
		{
			int tuoHit = LotteryUtil.getHitCount(sCountIntArray, drawNumberIntArray);
			int count = MathUtil.getC(tuoHit, 2 - fCount);
			if(count > 0)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, count, prizeDescription);
			}
		}
		return cp;
	}
}
