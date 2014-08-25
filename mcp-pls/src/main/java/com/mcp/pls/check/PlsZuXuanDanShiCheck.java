/**
 * 
 */
package com.mcp.pls.check;

import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.pls.common.PlsConstants;

/**
 * @author ming.li
 *
 */
public class PlsZuXuanDanShiCheck extends PlsCheck {

	/* (non-Javadoc)
	 * @see com.mcp.core.check.Check#check(com.mcp.core.model.batch.TicketBatch, java.lang.String[])
	 */
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		this.sort(intNumber);
		String[] ticketNumberArray = ticketNumber.split(";");
		for(int i = 0; i < ticketNumberArray.length; i++)
		{
			String[] dTicketNumberArray = ticketNumberArray[i].split(",");
			int[] intTicketNumberArray = new int[]{Integer.parseInt(dTicketNumberArray[0]), Integer.parseInt(dTicketNumberArray[1]), Integer.parseInt(dTicketNumberArray[2])};
			if(intNumber[0] == intTicketNumberArray[0] && intNumber[1] == intTicketNumberArray[1] && intNumber[2] == intTicketNumberArray[2])
			{
				if(PlsConstants.getNubmerType(intNumber) == PlsConstants.NUMBER_TYPE_ZUSAN)
				{
					super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
				}
				else
				{
					super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
				}
			}
		}
		return cp;
	}

	/**
	 * 冒泡排序
	 * @param number
	 */
	public void sort(int[] number)
	{
		for(int i = 1; i < number.length; i++)
		{
			for(int j = number.length - 1; j >= i; j--)
			{
				if(number[j] < number[j-1])
				{
					int temp = number[j];
					number[j] = number[j-1];
					number[j-1] = temp;
				}
			}
		}
	}
}
