/**
 * 
 */
package com.mcp.sfc.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;
import com.mcp.sfc.common.SfcConstants;

/**
 * @author ming.li
 *
 */
public class SfcRjcDanShiCheck extends SfcCheck {

	/* (non-Javadoc)
	 * @see com.mcp.order.batch.check.Check#check(com.mcp.order.batch.model.TicketBatch, java.lang.String[], com.mcp.order.model.entity.PrizeDescription)
	 */
	@Override
	public CheckParam check(TTicket ticket, String[] number,
			PrizeDescription prizeDescription) throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split(LotteryUtil.POSITION_REG_SEP);
		int hitCount = 0;
		for(int j = 0; j < ticketNumberArray.length; j++)
		{
			if(number[j].equals("*") || ticketNumberArray[j].indexOf(number[j]) > -1)
			{
				hitCount++;
			}
		}
		if(hitCount == SfcConstants.LEVEL_THIRD_HIT_COUNT)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
		}
		return cp;
	}

}
