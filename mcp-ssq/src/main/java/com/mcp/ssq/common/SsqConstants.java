/**
 * 
 */
package com.mcp.ssq.common;

import com.mcp.order.common.Constants;

/**
 * @author ming.li
 *
 */
public class SsqConstants {

	/**
	 * 双色球中奖规则
	 */
	public static final int[][] CHECK_RULE = new int[][]{{6,1,Constants.GRADE_LEVEL_FIRST}, {6,0,Constants.GRADE_LEVEL_SECOND}, {5,1,Constants.GRADE_LEVEL_THIRD}, {5,0,Constants.GRADE_LEVEL_FOURTH}, {4,1,Constants.GRADE_LEVEL_FOURTH}, {4,0,Constants.GRADE_LEVEL_FIFTH}, {3,1,Constants.GRADE_LEVEL_FIFTH}, {2,1,Constants.GRADE_LEVEL_SIXTH}, {1,1,Constants.GRADE_LEVEL_SIXTH}, {0,1,Constants.GRADE_LEVEL_SIXTH}};
	
	/**
	 * 单式红球长度
	 */
	public static final int DANSHI_RED_LEN = 6;
	
	/**
	 * 单式蓝球长度
	 */
	public static final int DANSHI_BLUE_LEN = 1;
	
	/**
	 * 最大蓝球号
	 */
	public static final int MAX_BLUE = 16;
	
	/**
	 * 最小蓝球号
	 */
	public static final int MIN_BLUE = 1;
	
	/**
	 * 最大红球号
	 */
	public static final int MAX_RED = 33;
	
	/**
	 * 最小红球号
	 */
	public static final int MIN_RED = 1;

}
