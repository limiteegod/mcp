/**
 * 
 */
package com.mcp.jqc.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;

/**
 * @author ming.li
 *
 */
public abstract class JqcCheck implements Check {

	/**
	 * 从prizeDescription中获取中奖金额
	 * @param checkParam
     * @param level
	 * @param count
     * @param prizeDescription
	 * @return
	 */
	protected void getPrizeByLevel(CheckParam checkParam, int level, int count, PrizeDescription prizeDescription)
	{
		GameGrade gradeForThisTerm = prizeDescription.getGradeByLevel(level);
        checkParam.incrBonus(level, count, gradeForThisTerm.getBonus());
	}

}
