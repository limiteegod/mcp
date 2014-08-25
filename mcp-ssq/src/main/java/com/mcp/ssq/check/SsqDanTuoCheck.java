package com.mcp.ssq.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.ssq.common.SsqConstants;

public class SsqDanTuoCheck extends SsqCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketStr = ticket.getNumbers();
		String[] ticketStrArray = ticketStr.split("\\|");
		String[] redArray = ticketStrArray[0].split("\\$");
		String[] blueArray = ticketStrArray[1].split("\\$");
		int redArrayLen = redArray.length;
		int blueArrayLen = blueArray.length;
		String[] redDanArray, redTuoArray, blueDanArray, blueTuoArray;
		if(redArrayLen > 1)
		{
			redDanArray = redArray[0].split(",");
			redTuoArray = redArray[1].split(",");
		}
		else
		{
			redDanArray = new String[]{};
			redTuoArray = redArray[0].split(",");
		}
		if(blueArrayLen > 1)
		{
			blueDanArray = blueArray[0].split(",");
			blueTuoArray = blueArray[1].split(",");
		}
		else
		{
			blueDanArray = new String[]{};
			blueTuoArray = blueArray[0].split(",");
		}
		int redDanLen = redDanArray.length, redTuoLen = redTuoArray.length, blueDanLen = blueDanArray.length, blueTuoLen = blueTuoArray.length;
		int redDanHit = 0, redTuoHit = 0, blueDanHit = 0, blueTuoHit = 0;
		for(int j = 0; j < 6; j++)
		{
			for(int i = 0; i < redDanLen; i++)
			{
				if(redDanArray[i].equals(number[j]))
				{
					redDanHit++;
				}
			}
			
			for(int i = 0; i < redTuoLen; i++)
			{
				if(redTuoArray[i].equals(number[j]))
				{
					redTuoHit++;
				}
			}
		}
		for(int j = 6; j < number.length; j++)
		{
			for(int i = 0; i < blueDanLen; i++)
			{
				if(blueDanArray[i].equals(number[j]))
				{
					blueDanHit++;
				}
			}
			
			for(int i = 0; i < blueTuoLen; i++)
			{
				if(blueTuoArray[i].equals(number[j]))
				{
					blueTuoHit++;
				}
			}
		}
		int[][] rule = SsqConstants.CHECK_RULE;
		for(int k = rule.length - 1; k >= 0; k--)
		{
			int[] dRule = rule[k];
			int redCount =  MathUtil.getC(redTuoHit, dRule[0] - redDanHit)*MathUtil.getC(redTuoLen - redTuoHit, (SsqConstants.DANSHI_RED_LEN - (dRule[0] - redDanHit) - redDanLen));
			int blueCount =  MathUtil.getC(blueTuoHit, dRule[1] - blueDanHit)*MathUtil.getC(blueTuoLen - blueTuoHit, SsqConstants.DANSHI_BLUE_LEN - (dRule[1] - blueDanHit) - blueDanLen);
			int count = redCount*blueCount;
			if(count > 0)
			{
				super.getPrizeByLevel(cp, dRule[2], count, prizeDescription);
			}
		}
		return cp;
	}

}
