/**
 * 
 */
package com.mcp.fsd.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;


/**
 * @author ming.li
 *
 */
public abstract class FsdCheck implements Check {
	
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

}
