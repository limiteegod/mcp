package com.mcp.qlc.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.qlc.common.QlcConstants;

public class QlcFuShiCheck extends QlcCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] baseArray = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2]), Integer.parseInt(number[3]),
				Integer.parseInt(number[4]), Integer.parseInt(number[5]), Integer.parseInt(number[6])};
		int[] specialArray = new int[]{Integer.parseInt(number[7])};
		String[] ticketNumberArray = ticketNumber.split(",");
		int[] ticketIntArray = new int[ticketNumberArray.length];
		for(int j = 0; j < ticketIntArray.length; j++)
		{
			ticketIntArray[j] = Integer.parseInt(ticketNumberArray[j]);
		}
		int redHit = 0, blueHit = 0;
		redHit = QlcConstants.getHitCount(baseArray, ticketIntArray);
		blueHit = QlcConstants.getHitCount(specialArray, ticketIntArray);
		int redNotHit = ticketIntArray.length - redHit - blueHit;
		int[][] rule = QlcConstants.CHECK_RULE;
		for(int k = rule.length - 1; k >= 0; k--)
		{
			int[] dRule = rule[k];
			int redCount = 0;
			if(dRule[1] == 0)
			{
				redCount = MathUtil.getC(redHit, dRule[0])*MathUtil.getC(redNotHit, QlcConstants.DANSHI_RED_LEN - dRule[0]);
			}
			else
			{
				if(blueHit == 1)
				{
					redCount = MathUtil.getC(redHit, dRule[0])*MathUtil.getC(redNotHit, QlcConstants.DANSHI_RED_LEN - dRule[0] - dRule[1]);
				}
			}
			if(redCount > 0)
			{
				super.getPrizeByLevel(cp, dRule[2], redCount, prizeDescription);
			}
		}
		return cp;
	}

}
