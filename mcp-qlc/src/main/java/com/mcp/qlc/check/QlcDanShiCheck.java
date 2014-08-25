package com.mcp.qlc.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import com.mcp.qlc.common.QlcConstants;

public class QlcDanShiCheck extends QlcCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		//红球开奖号码
		int[] pRedArray = LotteryUtil.getIntArrayFromStrArray(number, 0, QlcConstants.DANSHI_RED_LEN);
		//蓝球开奖号码
		int[] pSpecialArray = LotteryUtil.getIntArrayFromStrArray(number, QlcConstants.DANSHI_RED_LEN, QlcConstants.SPECIAL_LEN);
		String[] ticketArray = ticketNumber.split(LotteryUtil.TICKET_REG_SEP);
		for(int i = 0; i < ticketArray.length; i++)
		{
			String ticketStr = ticketArray[i];
			int[] ticketIntArray = LotteryUtil.getIntArrayFromStrArray(ticketStr.split(LotteryUtil.FUSHI_REG_SEP));
			//红球hit数目
			int redHit = LotteryUtil.getHitCount(pRedArray, ticketIntArray);
			//篮球hit数目
			int blueHit = LotteryUtil.getHitCount(pSpecialArray, ticketIntArray);
			int gradeLevel = -1;
			int[][] rule = QlcConstants.CHECK_RULE;
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
