/**
 * 
 */
package com.mcp.kt.check;

import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;
import org.apache.log4j.Logger;

/**
 * @author ming.li
 *
 */
public abstract class KtCheck implements Check {

    public static Logger log = Logger.getLogger(KtCheck.class);

    protected static int[] HEZHI_LEVEL_MAP = new int[]{-1, -1, -1, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
	
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

    protected int getLevelByHeZhi(int heZhi)
    {
        return HEZHI_LEVEL_MAP[heZhi];
    }

}
