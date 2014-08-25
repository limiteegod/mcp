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
public class SfcSscFuShiCheck extends SfcCheck {

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
		int cancelCount = 1;		//场次取消的注数的倍数
		int notHitCount = 0;		//猜中场次（不包含取消场次）的未猜中选项数目
		int lastNotHitIndex = -1;	//最后一个未猜中的场次号
		for(int j = 0; j < ticketNumberArray.length; j++)
		{
			if(number[j].equals("*"))
			{
				hitCount++;
				cancelCount *= ticketNumberArray[j].split(LotteryUtil.FUSHI_REG_SEP).length;
			}
			else if(ticketNumberArray[j].indexOf(number[j]) > -1)
			{
				hitCount++;
				notHitCount += ticketNumberArray[j].split(LotteryUtil.FUSHI_REG_SEP).length - 1;
			}
			else
			{
				lastNotHitIndex = j;
			}
		}
		if(hitCount == SfcConstants.LEVEL_FIRST_HIT_COUNT)
		{
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_FIRST, cancelCount, prizeDescription);
			
			//计算中二等奖的奖金
			int count = cancelCount*notHitCount;
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, count, prizeDescription);
		}
		else if(hitCount == SfcConstants.LEVEL_SECOND_HIT_COUNT)
		{
			//复式，中二等奖，则还需要判断中多少个二等奖，中二等奖的个数，是有未猜中场次决定的
			String notHitMatch = ticketNumberArray[lastNotHitIndex];
			int count = cancelCount*notHitMatch.split(LotteryUtil.FUSHI_REG_SEP).length;
			super.getPrizeByLevel(cp, Constants.GRADE_LEVEL_SECOND, count, prizeDescription);
		}
		return cp;
	}

}
