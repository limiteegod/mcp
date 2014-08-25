package com.mcp.dlt.check;

import com.mcp.dlt.common.DltConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

/**
 * 大乐透单式算奖算法的核心思想：算出红球hit(选中)数目，再算出篮球hit数组，如果和对应的奖级所要求
 * 的红球、篮球hit数目一致，则中此奖级。
 * @author ming.li
 */
public class DanShiCheck extends DltCheck {
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketNumber  = ticket.getNumbers();
		//红球开奖号码
		int[] pRedArray = LotteryUtil.getIntArrayFromStrArray(number, 0, DltConstants.DANSHI_RED_LEN);
		//蓝球开奖号码
		int[] pBlueArray = LotteryUtil.getIntArrayFromStrArray(number, DltConstants.DANSHI_RED_LEN, DltConstants.DANSHI_BLUE_LEN);
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
			int[][] rule = DltConstants.CHECK_RULE;
			for(int k = rule.length - 1; k >= 0; k--)
			{
				int[] detailRule = rule[k];
				if(detailRule[0] == redHit && detailRule[1] == blueHit)
				{
					gradeLevel = detailRule[2];
					break;	//只会中一个奖级的奖项（最高的奖级）
				}
			}
			if(gradeLevel > 0)
			{
				super.getPrizeByLevelAndPlayTypeCode(cp, gradeLevel, 1, ticket.getPlayTypeCode(), prizeDescription);
			}
		}
		return cp;
	}

}
