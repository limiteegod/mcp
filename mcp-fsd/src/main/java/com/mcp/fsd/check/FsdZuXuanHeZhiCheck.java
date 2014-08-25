package com.mcp.fsd.check;

import com.mcp.fsd.common.FsdConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;

public class FsdZuXuanHeZhiCheck extends FsdCheck {

	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		boolean hit = false;
		int heZhi = this.getHeZhi(number);
		String[] ticketNumberArray = ticketNumber.split(",");
		for(int j = 0; j < ticketNumberArray.length; j++)
		{
			if(Integer.parseInt(ticketNumberArray[j]) == heZhi)
			{
				hit = true;
				break;
			}
		}
		int[] intNumber = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2])};
		int type = FsdConstants.getNubmerType(intNumber);
		if(hit && type != FsdConstants.NUMBER_TYPE_BAOZI)
		{
			if(type == FsdConstants.NUMBER_TYPE_ZUSAN)
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, 1, prizeDescription);
			}
			else
			{
				super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_THIRD, 1, prizeDescription);
			}
		}
		return cp;
	}

	/**
	 * 获得单式号码的和值
	 * @param number
	 * @return
	 */
	public int getHeZhi(String[] number)
	{
		int value = 0;
		for(int i = 0; i < number.length; i++)
		{
			value += Integer.parseInt(number[i]);
		}
		return value;
	}
}
