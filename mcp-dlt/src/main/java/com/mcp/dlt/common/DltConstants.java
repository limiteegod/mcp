/**
 * 
 */
package com.mcp.dlt.common;

import com.mcp.order.common.Constants;

/**
 * @author ming.li
 *
 */
public class DltConstants {
	
	/**
	 * 大乐透中奖规则
	 */
	public static final int[][] CHECK_RULE = new int[][]{{5,2,Constants.GRADE_LEVEL_FIRST}, {5,1,Constants.GRADE_LEVEL_SECOND}, {5,0,Constants.GRADE_LEVEL_THIRD}, {4,2,Constants.GRADE_LEVEL_THIRD}, {4,1,Constants.GRADE_LEVEL_FOURTH}, {4,0,Constants.GRADE_LEVEL_FIFTH}, {3,2,Constants.GRADE_LEVEL_FOURTH}, {3,1,Constants.GRADE_LEVEL_FIFTH}, {2,2,Constants.GRADE_LEVEL_FIFTH}, {3,0,Constants.GRADE_LEVEL_SIXTH}, {1,2,Constants.GRADE_LEVEL_SIXTH}, {2,1,Constants.GRADE_LEVEL_SIXTH}, {0,2,Constants.GRADE_LEVEL_SIXTH}};
	
	/**
	 * 红球最大号的边界
	 */
	public final static int MAX_RED = 35;
	
	/**
	 * 红球最小号的边界
	 */
	public final static int MIN_RED = 1;
	
	/**
	 * 蓝球最大号的边界
	 */
	public final static int MAX_BLUE = 12;
	
	/**
	 * 蓝球最小号的边界
	 */
	public final static int MIN_BLUE = 1;
	
	public static final String PLAY_TYPE_ZHUIJIA = "05";
	
	/**
	 * 单式红球长度
	 */
	public static final int DANSHI_RED_LEN = 5;
	
	/**
	 * 单式蓝球长度
	 */
	public static final int DANSHI_BLUE_LEN = 2;
}
