/**
 * 
 */
package com.mcp.pls.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import com.mcp.order.util.LotteryUtil;


/**
 * @author ming.li
 *
 */
public abstract class PlsCheck implements Check {
	
	/**
	 * 固定奖，从gradeMap获取中奖金额
	 * @param level
	 * @return
	 */
	protected void getPrizeByLevel(CheckParam checkParam, int level, int count, PrizeDescription prizeDescription)
	{
		GameGrade gradeForThisTerm = prizeDescription.getGradeByLevel(level);
        checkParam.incrBonus(level, count, gradeForThisTerm.getBonus());
	}
	
	/**
	 * 直选复式（单式也是复式的一种）是否中奖，只对那些只有两种中奖状态的玩法（中0注，中1注）
	 * @param number
	 * @return
	 */
	protected boolean isZhiXuanFuShiHit(String number, int[] pNumberArray)
	{
		boolean hit = true;
		String[] numberArray = number.split(LotteryUtil.POSITION_REG_SEP);
		for(int j = 0; j < numberArray.length; j++)
		{
			int[] positionArray = LotteryUtil.getIntArrayFromStrArray(numberArray[j].split(LotteryUtil.FUSHI_REG_SEP));
			int hitCount = LotteryUtil.getHitCount(positionArray, new int[]{pNumberArray[j]});
			if(hitCount < 1)
			{
				hit = false;
				break;
			}
		}
		return hit;
	}
}
