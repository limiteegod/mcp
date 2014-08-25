/**
 * 
 */
package com.mcp.qlc.common;

import com.mcp.order.common.Constants;


/**
 * @author ming.li
 *
 */
public class QlcConstants {

	/**
	 * 七乐彩中奖规则，第一个数字为中基本好的数，第二个为中特别号的数目
	 */
	public static final int[][] CHECK_RULE = new int[][]{{7,0,Constants.GRADE_LEVEL_FIRST}, {6,1,Constants.GRADE_LEVEL_SECOND}, {6,0,Constants.GRADE_LEVEL_THIRD}, {5,1,Constants.GRADE_LEVEL_FOURTH}, {5,0,Constants.GRADE_LEVEL_FIFTH}, {4,1,Constants.GRADE_LEVEL_SIXTH}, {4,0,Constants.GRADE_LEVEL_SEVENTH}};
	
	/**
	 * 单式红球长度
	 */
	public static final int DANSHI_RED_LEN = 7;
	
	/**
	 * 特殊号码长度
	 */
	public static final int SPECIAL_LEN = 1;

	/**
	 * 最大红球号
	 */
	public static final int MAX_RED = 30;
	
	/**
	 * 最小红球号
	 */
	public static final int MIN_RED = 1;
	
	public static int getHitCount(int[] numberArrayOne, int[] numberArrayTwo)
	{
		int hitCount = 0;
		for(int i = 0; i < numberArrayOne.length; i++)
		{
			for(int j = 0; j < numberArrayTwo.length; j++)
			{
				if(numberArrayOne[i] == numberArrayTwo[j])
				{
					hitCount++;
					break;
				}
			}
		}
		return hitCount;
	}

}
