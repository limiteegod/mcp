package com.mcp.dlt.check;

import com.mcp.core.util.MathUtil;
import com.mcp.dlt.common.DltConstants;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.exception.CoreException;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.util.LotteryUtil;

/**
 * 大乐透复式算奖算法的核心思想：算出红球hit(选中)数目，再算出篮球hit数组，如果和对应的奖级所要求
 * 的红球、篮球hit数目及未中奖的红球、蓝球数目，排列组合的注数大于0，则中此奖级。
 * @author ming.li
 */
public class FuShiCheck extends DltCheck {

	//private static final Logger log = Logger.getLogger(FuShiCheck.class);
	
	@Override
	public CheckParam check(TTicket ticket, String[] number, PrizeDescription prizeDescription) throws CoreException {
		CheckParam cp = new CheckParam();
		String ticketStr = ticket.getNumbers();
		String[] ticketStrArray = ticketStr.split(LotteryUtil.POSITION_REG_SEP);
		int[] redArray = LotteryUtil.getIntArrayFromStrArray(ticketStrArray[0].split(LotteryUtil.FUSHI_REG_SEP));
		int[] blueArray = LotteryUtil.getIntArrayFromStrArray(ticketStrArray[1].split(LotteryUtil.FUSHI_REG_SEP));
		//红球开奖号码
		int[] pRedArray = LotteryUtil.getIntArrayFromStrArray(number, 0, DltConstants.DANSHI_RED_LEN);
		//蓝球开奖号码
		int[] pBlueArray = LotteryUtil.getIntArrayFromStrArray(number, DltConstants.DANSHI_RED_LEN, DltConstants.DANSHI_BLUE_LEN);
		int redLen = redArray.length, blueLen = blueArray.length;
		//红球hit数目
		int redHit = LotteryUtil.getHitCount(pRedArray, redArray);
		//篮球hit数目
		int blueHit = LotteryUtil.getHitCount(pBlueArray, blueArray);
		int redNotHit = redLen - redHit, blueNotHit = blueLen - blueHit;
		int[][] rule = DltConstants.CHECK_RULE;
		for(int k = rule.length - 1; k >= 0; k--)
		{
			int[] dRule = rule[k];
			int redCount = MathUtil.getC(redHit, dRule[0])*MathUtil.getC(redNotHit, DltConstants.DANSHI_RED_LEN - dRule[0]);
			int blueCount = MathUtil.getC(blueHit, dRule[1])*MathUtil.getC(blueNotHit, DltConstants.DANSHI_BLUE_LEN - dRule[1]);
			int count = redCount*blueCount;
			if(count > 0)
			{
				super.getPrizeByLevelAndPlayTypeCode(cp, dRule[2], count, ticket.getPlayTypeCode(), prizeDescription);
			}
		}
		return cp;
	}

}
