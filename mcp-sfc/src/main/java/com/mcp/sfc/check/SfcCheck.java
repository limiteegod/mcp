/**
 * 
 */
package com.mcp.sfc.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;

/**
 * @author ming.li
 *
 */
public abstract class SfcCheck implements Check {

	/**
	 * 从prizeDescription中获取中奖金额
	 * @param level
	 * @param playTypeCode
	 * @param cp
	 * @return
	 */
	protected void getPrizeByLevel(CheckParam checkParam, int level, int count, PrizeDescription prizeDescription)
	{
		GameGrade gradeForThisTerm = prizeDescription.getGradeByLevel(level);
        checkParam.incrBonus(level, count, gradeForThisTerm.getBonus());
	}
}
