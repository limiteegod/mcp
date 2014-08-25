package com.mcp.ssq.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import com.mcp.ssq.common.SsqConstants;

public class SsqDanShiCheck extends SsqCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		//红球开奖号码
		int[] pRedArray = LotteryUtil.getIntArrayFromStrArray(number, 0, SsqConstants.DANSHI_RED_LEN);
		//蓝球开奖号码
		int[] pBlueArray = LotteryUtil.getIntArrayFromStrArray(number, SsqConstants.DANSHI_RED_LEN, SsqConstants.DANSHI_BLUE_LEN);
		String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
		for(int i = 0; i < ticketArray.length; i++)
		{
			String ticketStr = ticketArray[i];
			String[] ticketNumberArray = ticketStr.split(LotteryUtil.POSITION_REG_SEP);
			//票面红球号码
			int[] tRedArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberArray[0].split(LotteryUtil.FUSHI_REG_SEP));
			//票面篮球号码
			int[] tBlueArray = LotteryUtil.getIntArrayFromStrArray(ticketNumberArray[1].split(LotteryUtil.FUSHI_REG_SEP));
			//红球hit数目
			int redHit = LotteryUtil.getHitCount(pRedArray, tRedArray);
			//篮球hit数目
			int blueHit = LotteryUtil.getHitCount(pBlueArray, tBlueArray);
			int gradeLevel = -1;
			int[][] rule = SsqConstants.CHECK_RULE;
			for(int k = rule.length - 1; k >= 0; k--)
			{
				int[] detailRule = rule[k];
				if(detailRule[0] == redHit && detailRule[1] == blueHit)
				{
					gradeLevel = detailRule[2];
					break;
				}
			}
			if(gradeLevel > 0)
			{
				super.getPrizeByLevel(cp, gradeLevel, 1, prizeDescription);
			}
		}
		return cp;
	}

}
