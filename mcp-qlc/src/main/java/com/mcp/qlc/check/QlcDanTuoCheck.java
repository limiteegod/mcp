package com.mcp.qlc.check;

import com.mcp.core.util.MathUtil;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.qlc.common.QlcConstants;

public class QlcDanTuoCheck extends QlcCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription)
			throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		String[] ticketNumberArray = ticketNumber.split("\\$");
		int[] baseArray = new int[]{Integer.parseInt(number[0]), Integer.parseInt(number[1]), Integer.parseInt(number[2]), Integer.parseInt(number[3]),
				Integer.parseInt(number[4]), Integer.parseInt(number[5]), Integer.parseInt(number[6])};
		int[] specialArray = new int[]{Integer.parseInt(number[7])};
		
		String[] ticketDanNumberArray = ticketNumberArray[0].split(",");
		int[] ticketDanIntArray = new int[ticketDanNumberArray.length];
		for(int j = 0; j < ticketDanIntArray.length; j++)
		{
			ticketDanIntArray[j] = Integer.parseInt(ticketDanNumberArray[j]);
		}
		int redDanHit = 0, blueDanHit = 0;
		redDanHit = QlcConstants.getHitCount(baseArray, ticketDanIntArray);
		blueDanHit = QlcConstants.getHitCount(specialArray, ticketDanIntArray);
		//int redDanNotHit = ticketDanIntArray.length - redDanHit - blueDanHit;
		
		String[] ticketTuoNumberArray = ticketNumberArray[1].split(",");
		int[] ticketTuoIntArray = new int[ticketTuoNumberArray.length];
		for(int j = 0; j < ticketTuoIntArray.length; j++)
		{
			ticketTuoIntArray[j] = Integer.parseInt(ticketTuoNumberArray[j]);
		}
		int redTuoHit = 0, blueTuoHit = 0;
		redTuoHit = QlcConstants.getHitCount(baseArray, ticketTuoIntArray);
		blueTuoHit = QlcConstants.getHitCount(specialArray, ticketTuoIntArray);
		int redTuoNotHit = ticketTuoIntArray.length - redTuoHit - blueTuoHit;
		
		int[][] rule = QlcConstants.CHECK_RULE;
		for(int k = rule.length - 1; k >= 0; k--)
		{
			int[] dRule = rule[k];
			int needHitTuoRed = dRule[0] - redDanHit;
			int needHitTuoBlue = dRule[1] - blueDanHit;
			int needRedTuoNotHit = 7 - ticketDanNumberArray.length - needHitTuoRed - needHitTuoBlue;
			int count = MathUtil.getC(redTuoHit, needHitTuoRed)*MathUtil.getC(blueTuoHit, needHitTuoBlue)*MathUtil.getC(redTuoNotHit, needRedTuoNotHit);
			if(count > 0)
			{
				super.getPrizeByLevel(cp, dRule[2], count, prizeDescription);
			}
		}
		return cp;
	}

}
