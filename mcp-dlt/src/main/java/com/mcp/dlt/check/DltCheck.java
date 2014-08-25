/**
 * 
 */
package com.mcp.dlt.check;

import com.mcp.dlt.common.DltConstants;
import com.mcp.order.batch.check.Check;
import com.mcp.order.batch.check.CheckParam;
import com.mcp.order.common.Constants;
import com.mcp.order.model.entity.PrizeDescription;
import com.mcp.order.model.ts.GameGrade;

/**
 * @author ming.li
 *
 */
public abstract class DltCheck implements Check {

    /**
     * 计算税前税后奖金额度
     * @param checkParam
     * @param level 奖级
     * @param count 注数
     * @param playTypeCode  玩法
     * @param prizeDescription  奖级金额明细
     */
	protected void getPrizeByLevelAndPlayTypeCode(CheckParam checkParam, int level, int count, String playTypeCode, PrizeDescription prizeDescription)
	{
		GameGrade gradeForThisTerm = prizeDescription.getGradeByLevel(level);
        checkParam.incrBonus(level, count, gradeForThisTerm.getBonus());
		if(playTypeCode.equals(DltConstants.PLAY_TYPE_ZHUIJIA) && level != Constants.GRADE_LEVEL_SIXTH)
		{
            checkParam.incrBonus(level, count, prizeDescription.getGradeByLevel(level + 6).getBonus());
		}
	}
}
