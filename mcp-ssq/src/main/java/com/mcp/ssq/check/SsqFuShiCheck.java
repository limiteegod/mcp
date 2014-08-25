package com.mcp.ssq.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import com.mcp.ssq.common.SsqConstants;

public class SsqFuShiCheck extends SsqCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketStr = ticket.getNumbers();
		String[] ticketStrArray = ticketStr.split(LotteryUtil.POSITION_REG_SEP);
		int[] redArray = LotteryUtil.getIntArrayFromStrArray(ticketStrArray[0].split(LotteryUtil.FUSHI_REG_SEP));
		int[] blueArray = LotteryUtil.getIntArrayFromStrArray(ticketStrArray[1].split(LotteryUtil.FUSHI_REG_SEP));
		//红球开奖号码
		int[] pRedArray = LotteryUtil.getIntArrayFromStrArray(number, 0, SsqConstants.DANSHI_RED_LEN);
		//蓝球开奖号码
		int[] pBlueArray = LotteryUtil.getIntArrayFromStrArray(number, SsqConstants.DANSHI_RED_LEN, SsqConstants.DANSHI_BLUE_LEN);
		int redLen = redArray.length, blueLen = blueArray.length;
		//红球hit数目
		int redHit = LotteryUtil.getHitCount(pRedArray, redArray);
		//篮球hit数目
		int blueHit = LotteryUtil.getHitCount(pBlueArray, blueArray);
		int redNotHit = redLen - redHit, blueNotHit = blueLen - blueHit;
		
		int[][] rule = SsqConstants.CHECK_RULE;
		for(int k = rule.length - 1; k >= 0; k--)
		{
			int[] dRule = rule[k];
			int redCount = MathUtil.getC(redHit, dRule[0])*MathUtil.getC(redNotHit, SsqConstants.DANSHI_RED_LEN - dRule[0]);
			int blueCount = MathUtil.getC(blueHit, dRule[1])*MathUtil.getC(blueNotHit, SsqConstants.DANSHI_BLUE_LEN - dRule[1]);
			int count = redCount*blueCount;
			if(count > 0)
			{
				super.getPrizeByLevel(cp, dRule[2], count, prizeDescription);
			}
		}
		return cp;
	}

}
