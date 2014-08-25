package com.mcp.qxc.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.qxc.common.QxcConstants;

public class QxcDanshiCheck extends QxcCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketArray = ticketNumber.split(";");
		for(int i = 0; i < ticketArray.length; i++)
		{
			String ticketStr = ticketArray[i];
			String[] ticketNumberArray = ticketStr.split("\\|");
			int maxHit = 0, hit = 0, gradeLevel = -1;
			for(int j = 0; j < QxcConstants.DANSHI_LEN; j++)
			{
				if(ticketNumberArray[j].equals(number[j]))
				{
					hit++;
				}
				else
				{
					if(hit > maxHit)
					{
						maxHit = hit;
					}
					hit = 0;
				}
			}
			if(hit > maxHit)	//循环结束再判断一次，因为当最后一个号码匹配时，hit有可能比maxHit大
			{
				maxHit = hit;
			}
            System.out.println(maxHit);
			int[][] rule = QxcConstants.CHECK_RULE;
			for(int k = rule.length - 1; k >= 0; k--)
			{
				int[] detailRule = rule[k];
				if(detailRule[0] == maxHit)
				{
					gradeLevel = detailRule[1];
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
